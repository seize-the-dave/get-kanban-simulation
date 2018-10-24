package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActivityColumn implements Column, Limited {
    private final Activity activity;
    private final Column upstream;
    private final Queue<Card> todo;
    private final Queue<Card> done;
    private List<ActivityDice> dice;
    private int sum;
    private int limit;

    public ActivityColumn(Activity activity, int limit, Column upstream) {
        this.activity = activity;
        this.upstream = upstream;
        this.limit = limit;
        this.todo = new PriorityQueue<>(new DefaultPrioritisationStrategy());
        this.done = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    }

    public ActivityColumn(Activity analysis, Column upstream) {
        this(analysis, Integer.MAX_VALUE, upstream);
    }

    public void addCard(Card card) {
        if (getCards().size() == this.limit) {
            throw new IllegalStateException();
        }
        if (card.getRemainingWork(this.activity) == 0) {
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

    public void allocateDice(ActivityDice... dice) {
        this.dice = Arrays.asList(dice);
        this.sum = this.dice.stream().mapToInt(d -> d.rollFor(activity)).sum();
    }

    public List<ActivityDice> getAllocatedDice() {
        return this.dice;
    }

    public void visit(Day day) {
        // Pull
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
            int remaining = card.getRemainingWork(activity);
            card.doWork(activity, Math.min(remaining, sum));
            sum -= remaining;
            if (card.getRemainingWork(activity) == 0) {
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
}
