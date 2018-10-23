package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.*;

public class ActivityColumn implements Column {
    private final Activity activity;
    private final Column upstream;
    private final Queue<Card> todo;
    private final Queue<Card> done;
    private List<ActivityDice> dice;
    private int sum;

    public ActivityColumn(Activity activity, Column upstream) {
        this.activity = activity;
        this.upstream = upstream;
        this.todo = new PriorityQueue<>(new DefaultPrioritisationStrategy());
        this.done = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    }

    public void addCard(Card card) {
        if (card.getRemainingWork(this.activity) == 0) {
            done.add(card);
        } else {
            todo.add(card);
        }
    }

    public Collection<Card> getIncompleteCards() {
        return todo;
    }

    public Optional<Card> pullCard() {
        return Optional.ofNullable(done.poll());
    }

    public void allocateDice(ActivityDice... dice) {
        this.dice = Arrays.asList(dice);
        this.sum = this.dice.stream().mapToInt(d -> d.rollFor(activity)).sum();
    }

    public List<ActivityDice> getAllocatedDice() {
        return this.dice;
    }

    public void doWork() {
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

    public void pullFromUpstream(int day) {
        upstream.pullFromUpstream(day);
        Optional<Card> card = upstream.pullCard();
        if (card.isPresent()) {
            addCard(card.get());
        }
    }
}
