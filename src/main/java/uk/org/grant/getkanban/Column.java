package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.*;
import java.util.stream.Collectors;

public class Column {
    private final Activity activity;
    private final SortedSet<Card> cards = new TreeSet<>(new DefaultPrioritisationStrategy());
    private final Column upstream;
    private int sum;

    public Column(Activity activity, Column upstream) {
        this.activity = activity;
        this.upstream = upstream;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Collection<Card> getCards() {
        return cards;
    }

    public List<Card> getCompletedCards() {
        return cards.stream().filter(p -> p.getRemainingWork(this.activity) == 0).collect(Collectors.toList());
    }

    public void allocateDice(ActivityDice... dice) {
        this.sum = Arrays.asList(dice).stream().mapToInt(d -> d.rollFor(activity)).sum();
    }

    public void doWork() {
        for (Card card : cards) {
            int remaining = card.getRemainingWork(activity);
            card.doWork(activity, Math.min(remaining, sum));
            sum -= remaining;
            if (sum < 1) {
                break;
            }
        }
    }

    public void pull() {
        cards.addAll(upstream.getCompletedCards());
    }

    public static class DefaultPrioritisationStrategy implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            return 0;
        }
    }
}
