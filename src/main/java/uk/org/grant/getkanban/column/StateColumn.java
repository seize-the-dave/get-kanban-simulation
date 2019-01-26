package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.policies.prioritisation.WipAgingPrioritisationStrategy;
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
    private final Column upstream;
    private final MutablePriorityQueue<Card> todo;
    private final MutablePriorityQueue<Card> done;
    private AtomicBoolean rolled = new AtomicBoolean();
    private List<DiceGroup> groups = new ArrayList<>();
    private Comparator<Card> comparator;
    private Set<ColumnListener> listeners = new HashSet<>();

    public StateColumn(State state, int limit, Column upstream) {
        super(limit);
        this.state = state;
        this.upstream = upstream;
        this.todo = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.done = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.comparator = new WipAgingPrioritisationStrategy();
        this.logger = LoggerFactory.getLogger(StateColumn.class.getName() + "[" + state + "]");
    }

    public StateColumn(State state, Column upstream) {
        this(state, Integer.MAX_VALUE, upstream);
    }

    @Override
    public void addCard(Card card) {
        if (getCards().size() == getLimit()) {
            throw new IllegalStateException();
        }
        if (card.getRemainingWork(this.state) == 0) {
            done.add(card);
        } else {
            listeners.forEach(l -> l.cardAdded(card));
            todo.add(card);
        }
    }

    @Override
    public Queue<Card> getCards() {
        Queue<Card> queue = new PriorityQueue<>(comparator);
        queue.addAll(Stream.concat(this.todo.stream(), this.done.stream()).collect(Collectors.toList()));

        return queue;
    }

    public Collection<Card> getIncompleteCards() {
        return todo;
    }

    @Override
    public Optional<Card> pull(Context context) {
        doTheWork(context);
        return Optional.ofNullable(done.poll());
    }

    public void doTheWork(Context context) {
        reduceWorkOnAssignedTickets();
        spendLeftoverPoints(context);
    }

    private void spendLeftoverPoints(Context context) {
        // Pull from upstream until we reach our limit, or upstream has nothing left to give
        while (getCards().size() < this.getLimit()) {
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
                logger.info("{}: {} -> {} -> {}", context.getDay(), upstream, optionalCard.get().getName(), this);
            } else {
                break;
            }
        }
        // No dice, so no point in continuing
        if (groups.size() == 0) {
            return;
        }
        // No cards we can work on, so no point in continuing
        if (todo.stream().filter(c -> c.isBlocked() == false).count() == 0) {
            return;
        }
        logger.info("{}: Spending leftover points on {}", context.getDay(), this);
        for (DiceGroup group : groups) {
            logger.info("{}: {} leftover points to spend", context.getDay(), group.getLeftoverPoints());
            // Do as much work as possible on unblocked tickets
            todo.stream().filter(c -> c.isBlocked() == false).forEach(c -> group.spendLeftoverPoints(state, c));
            // Add all the completed tickets to done
            todo.stream().filter(c -> c.getRemainingWork(state) == 0).forEach(c -> {
                done.add(c);
                logger.info("{} -> {} -> {}:DONE", state, c.getName(), state);
            });
            // Remove the completed tickets
            todo.removeIf(c -> c.getRemainingWork(state) == 0);
        }
        // Remove spent groups
        groups.removeIf(g -> g.getLeftoverPoints() == 0);
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
            Optional<Card> card = todo.stream().filter(c -> c.getRemainingWork(this.state) == 0).findFirst();
            if (card.isPresent()) {
                logger.info("{} -> {} -> {}:DONE", state, card.get().getName(), state);
                todo.remove(card.get());
                done.add(card.get());
            }
        }
        groups.removeIf(g -> g.getLeftoverPoints() == 0);
    }

    @Override
    public String toString() {
        String wip = getLimit() == Integer.MAX_VALUE ? "∞" : Integer.toString(getLimit());
        return "[" + this.state + " (" + todo.size() + "/" + done.size() + "/" + wip+ ")]";
    }

    public void assignDice(DiceGroup... groups) {
        this.groups = new LinkedList<>(Arrays.asList(groups));
        this.rolled.set(false);
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        this.comparator = comparator;

        todo.setComparator(comparator);
        done.setComparator(comparator);
    }

    public void addListener(ColumnListener columnListener) {
        listeners.add(columnListener);
    }
}
