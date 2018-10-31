package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
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
        this.todo = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
        this.done = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
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

    public void visit(Context context) {
//        System.out.println("In " + this + " on " + context.getDay());
//        upstream.visit(day);
        while (getCards().size() < this.limit) {
//            System.out.println("Try pulling from " + upstream);
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
//                System.out.println("Pulled " + optionalCard.get() + " into " + this);
            } else {
//                System.out.println(upstream + " has nothing to pull.");
                break;
            }
        }
        // Do Work
        for (Iterator<Card> iter = todo.iterator(); iter.hasNext(); ) {
            Card card = iter.next();
            int remaining = card.getRemainingWork(this.state);
            int work_to_be_done = Math.min(remaining, sum);
//            System.out.println("Doing " + work_to_be_done + " points of work on " + card);
            card.doWork(this.state, work_to_be_done);
            sum -= work_to_be_done;
            if (card.getRemainingWork(this.state) == 0) {
                iter.remove();
                done.add(card);
//                System.out.println(card + " has been completed in " + this);
            }
            if (sum < 1) {
                break;
            }
        }
//        System.out.println(this + " has " + sum + " unspent point(s)");
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "[" + this.state + " (" + todo.size() + "/" + done.size() + "/" + limit + ")]";
    }
}
