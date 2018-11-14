package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
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
    private final Column upstream;
    private final MutablePriorityQueue<Card> todo;
    private final MutablePriorityQueue<Card> done;
    private AtomicBoolean rolled = new AtomicBoolean();
    private DiceGroup[] groups = new DiceGroup[0];
    private Comparator<Card> comparator;

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
        logger.info("{}: Doing work in {} ", context.getDay(), this);
        reduceWorkOnAssignedTickets();
        spendLeftoverPoints(context);
    }

    private void spendLeftoverPoints(Context context) {
        logger.info("{}: Spending leftover points on {}", context.getDay(), this);
        while (getCards().size() < this.getLimit()) {
            logger.info("Pull from {}", upstream);
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
                logger.info("Pulled {} into {} from {}", optionalCard.get(), this, upstream);
            } else {
                logger.warn("{} has nothing available to pull", upstream);
            }
            // Do Work
            if (todo.isEmpty()) {
                break;
            }
            if (Arrays.stream(groups).noneMatch(g -> g.getLeftoverPoints() > 0)) {
                break;
            }
            for (DiceGroup group : groups) {
                logger.info("{} leftover points to spend", group.getLeftoverPoints());
                for (Iterator<Card> iter = todo.iterator(); iter.hasNext() && group.getLeftoverPoints() > 0; ) {
                    Card card = iter.next();
                    group.spendLeftoverPoints(state, card);
                    if (card.getRemainingWork(this.state) == 0) {
                        iter.remove();
                        done.add(card);
                        logger.info("{} has been completed and is now ready to pull", card);
                    }
                }
            }
        }
        if (getCards().size() == this.getLimit()) {
            logger.info("{} is filled to capacity", this);
        }
    }

    private void reduceWorkOnAssignedTickets() {
        if (rolled.getAndSet(true)) {
            return;
        }
        for (DiceGroup group : groups) {
            group.rollFor(state);
            Optional<Card> card = todo.stream().filter(c -> c.getRemainingWork(this.state) == 0).findFirst();
            if (card.isPresent()) {
                logger.info("{} has been completed and is now ready to pull", card.get());
                todo.remove(card.get());
                done.add(card.get());
            }
        }
    }

    @Override
    public String toString() {
        String wip = getLimit() == Integer.MAX_VALUE ? "∞" : Integer.toString(getLimit());
        return "[" + this.state + " (" + todo.size() + "/" + done.size() + "/" + wip+ ")]";
    }

    public void assignDice(DiceGroup... groups) {
        this.groups = groups;
        this.rolled.set(false);
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        this.comparator = comparator;

        todo.setComparator(comparator);
        done.setComparator(comparator);
    }
}
