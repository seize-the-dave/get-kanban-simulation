package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateColumn implements Column {
    private final State state;
    private final Column upstream;
    private final Queue<Card> todo;
    private final Queue<Card> done;
    private List<StateDice> dice;
    private int sum;
    private int limit;

    public StateColumn(State state, int limit, Column upstream) {
        this.state = state;
        this.upstream = upstream;
        this.limit = limit;
        this.todo = new PriorityQueue<>(new DefaultPrioritisationStrategy());
        this.done = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    }

    public StateColumn(State state, Column upstream) {
        this(state, Integer.MAX_VALUE, upstream);
    }

    public void addCard(Card card) {
        if (getCards().size() == this.limit) {
            throw new IllegalStateException();
        }
        if (card.getRemainingWork(this.state) == 0) {
            done.add(card);
        } else {
            todo.add(card);
        }
    }

    @Override
    public Collection<Card> getCards() {
        return Stream.concat(this.todo.stream(), this.done.stream()).collect(Collectors.toList());
    }

    public Collection<Card> getIncompleteCards() {
        return todo;
    }

    public Optional<Card> pull() {
        return Optional.ofNullable(done.poll());
    }

    public void allocateDice(StateDice... dice) {
        this.dice = Arrays.asList(dice);
        this.sum = this.dice.stream().mapToInt(d -> d.rollFor(this.state)).sum();
    }

    public List<StateDice> getAllocatedDice() {
        return this.dice;
    }

    public void visit(Day day) {
        upstream.visit(day);
        if (getCards().size() < this.limit) {
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
            }
        }
        // Do Work
        for (Iterator<Card> iter = todo.iterator(); iter.hasNext(); ) {
            Card card = iter.next();
            int remaining = card.getRemainingWork(this.state);
            card.doWork(this.state, Math.min(remaining, sum));
            sum -= remaining;
            if (card.getRemainingWork(this.state) == 0) {
                iter.remove();
                done.add(card);
            }
            if (sum < 1) {
                break;
            }
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
