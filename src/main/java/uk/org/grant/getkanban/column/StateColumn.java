package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.DiceGroup;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateColumn extends LimitedColumn {
    private final Logger logger;
    private final State state;
    private final Column standard;
    private final Column expedite;
    private final MutablePriorityQueue<Card> stdTodo;
    private final MutablePriorityQueue<Card> stdDone;
    private final MutablePriorityQueue<Card> expTodo;
    private final MutablePriorityQueue<Card> expDone;
    private AtomicBoolean rolled = new AtomicBoolean();
    private List<DiceGroup> groups = new ArrayList<>();
    private Comparator<Card> comparator;
    private Set<ColumnListener> listeners = new HashSet<>();
    private boolean secondaryWorkers = true;

    public StateColumn(State state, int limit, Column standard, Column expedite) {
        super(limit);
        this.state = state;
        this.standard = standard;
        this.expedite = expedite;
        this.stdTodo = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.stdDone = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.expTodo = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.expDone = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.comparator = new WipAgingPrioritisationStrategy();
        this.logger = LoggerFactory.getLogger(StateColumn.class.getName() + "[" + state + "]");
    }

    public StateColumn(State state, Column standard, Column expedite) {
        this(state, Integer.MAX_VALUE, standard, expedite);
    }

    @Override
    public void addCard(Card card, ClassOfService cos) {
        if (getCards(cos).size() == getLimit(cos)) {
            throw new IllegalStateException("Too many cards in " + cos);
        }
        if (card.getRemainingWork(this.state) == 0) {
            done(cos).add(card);
        } else {
            listeners.forEach(l -> l.cardAdded(card));
            todo(cos).add(card);
        }
    }

    @Override
    public List<Card> getCards() {
        return Stream.concat(
                this.todo(ClassOfService.STANDARD).stream(),
                Stream.concat(
                        this.done(ClassOfService.STANDARD).stream(),
                        Stream.concat(
                                this.todo(ClassOfService.EXPEDITE).stream(),
                                this.done(ClassOfService.EXPEDITE).stream()
                        )
                )
        ).sorted(comparator).collect(Collectors.toList());
    }

    public Queue<Card> getCards(ClassOfService cos) {
        Queue<Card> queue = new PriorityQueue<>(comparator);
        queue.addAll(
                Stream.concat(
                        this.todo(cos).stream(),
                        this.done(cos).stream()
                ).collect(Collectors.toList())
        );

        return queue;
    }

    /**
     * Called during the stand up
     */
    public void assignDice(DiceGroup... groups) {
        this.groups = new LinkedList<>(Arrays.asList(groups));
        this.rolled.set(false);
    }

    /**
     * Find all the incomplete cards.
     *
     * Used for allocating dice (and blocking the first incomplete card)
     */
    public Collection<Card> getIncompleteCards() {
        Queue<Card> queue = new PriorityQueue<>(comparator);
        queue.addAll(
                Stream.concat(
                        this.todo(ClassOfService.EXPEDITE).stream(),
                        this.todo(ClassOfService.STANDARD).stream()
                ).collect(Collectors.toList())
        );

        return queue;
    }

    // Mostly called during the doTheWork phase

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        doTheWork(context);
        return Optional.ofNullable(done(cos).poll());
    }

    public void doTheWork(Context context) {
        reduceWorkOnAssignedTickets();
        spendLeftoverPoints(context, ClassOfService.EXPEDITE);
        spendLeftoverPoints(context, ClassOfService.STANDARD);
    }

    private Queue<Card> todo(ClassOfService cos) {
        if (cos == ClassOfService.STANDARD) {
            return stdTodo;
        } else {
            return expTodo;
        }
    }

    private Queue<Card> done(ClassOfService cos) {
        if (cos == ClassOfService.STANDARD) {
            return stdDone;
        } else {
            return expDone;
        }
    }

    private Column upstream(ClassOfService cos) {
        if (cos == ClassOfService.STANDARD) {
            return standard;
        } else {
            return expedite;
        }
    }

    private int getLimit(ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            // No column limits for expedite!
            return Integer.MAX_VALUE;
        } else {
            return getLimit();
        }
    }

    /**
     * TODO: What if we have a load of leftover points?  Shouldn't we keep pulling?
     */
    private void spendLeftoverPoints(Context context, ClassOfService cos) {
        // Pull from standard until we reach our limit, or standard has nothing left to give
        while (getCards(cos).size() < this.getLimit(cos)) {
            Optional<Card> optionalCard = upstream(cos).pull(context, cos);
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get(), cos);
                logger.info("{}: {} -> {} -> {} ({})", context.getDay(), upstream(cos), optionalCard.get().getName(), this, cos);
            } else {
                logger.error("STARVATION: {} has nothing to pull in {} swimlane.", upstream(cos), cos);
                break;
            }
        }
        // No dice, so no point in continuing
        if (groups.size() == 0) {
            return;
        }
        // No cards we can work on, so no point in continuing
        if (todo(cos).stream().filter(c -> c.isBlocked() == false).count() == 0) {
            return;
        }
        logger.info("{}: Spending leftover points on {} ({})", context.getDay(), this, cos);
        for (DiceGroup group : groups) {
            logger.info("{}: {} leftover points to spend ({})", context.getDay(), group.getLeftoverPoints(), cos);
            // Do as much work as possible on unblocked tickets
            todo(cos).stream().filter(c -> c.isBlocked() == false).forEach(c -> group.spendLeftoverPoints(state, c));
            // Add all the completed tickets to stdDone
            todo(cos).stream().filter(c -> c.getRemainingWork(state) == 0).forEach(c -> {
                done(cos).add(c);
                logger.info("{} -> {} -> {}:DONE ({})", state, c.getName(), state, cos);
            });
            // Remove the completed tickets
            todo(cos).removeIf(c -> c.getRemainingWork(state) == 0);
        }
        // Remove spent groups
        groups.removeIf(g -> g.getLeftoverPoints() == 0);
        int leftovers = 0;
        for (DiceGroup group : groups) {
            leftovers += group.getLeftoverPoints();
        }
        logger.info("{} has {} leftover points", this, leftovers);
    }

    private void reduceWorkOnAssignedTickets() {
        if (rolled.getAndSet(true)) {
            return;
        }
        if (groups.size() == 0) {
            return;
        }
        logger.info("Reducing work on cards with assigned dice");
        for (DiceGroup group : groups) {
            group.rollFor(state);
            Card groupCard = group.getCard();
            logger.info("{} has {} points left", groupCard, groupCard.getRemainingWork(this.state));
            for (ClassOfService cos : ClassOfService.values()) {
                if (groupCard.getRemainingWork(state) == 0 && todo(cos).contains(groupCard)) {
                    todo(cos).remove(groupCard);
                    done(cos).add(groupCard);
                }
            }
        }
        groups.removeIf(g -> g.getLeftoverPoints() == 0);
    }

    @Override
    public String toString() {
        String wip = getLimit() == Integer.MAX_VALUE ? "∞" : Integer.toString(getLimit());
        return "[" + this.state + " (" + stdTodo.size() + "/" + stdDone.size() + "/" + wip+ ")]";
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        this.comparator = comparator;

        stdTodo.setComparator(comparator);
        stdDone.setComparator(comparator);
    }

    public void addListener(ColumnListener columnListener) {
        listeners.add(columnListener);
    }

    public void expediteTickets(Day day) {
        List<Card> expeditables = todo(ClassOfService.STANDARD).stream().filter(c -> c.isExpeditable(day)).collect(Collectors.toList());
        todo(ClassOfService.STANDARD).removeAll(expeditables);
        todo(ClassOfService.EXPEDITE).addAll(expeditables);

        if (expeditables.size() > 0) {
            logger.info("Expedited {}", expeditables);
        }
    }

    /**
     * Removes all the cards from this column
     */
    public void clear() {
        this.todo(ClassOfService.EXPEDITE).clear();
        this.todo(ClassOfService.STANDARD).clear();
        this.done(ClassOfService.EXPEDITE).clear();
        this.done(ClassOfService.STANDARD).clear();
    }

    public boolean canAssignSecondaryWorkers() {
        return secondaryWorkers;
    }

    public void disableSecondaryWorkers() {
        this.secondaryWorkers = false;
    }

    public void enableSecondaryWorkers() {
        this.secondaryWorkers = true;
    }
}
