package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Column {
    private final Activity activity;
    private final List<Card> cards = new ArrayList<>();
    private final Column upstream;
    private List<ActivityDice> dice;

    public Column(Activity activity, Column upstream) {
        this.activity = activity;
        this.upstream = upstream;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getCards(Predicate<Card> predicate) {
        return cards.stream().filter(predicate).collect(Collectors.toList());
    }

    public void allocateDice(ActivityDice... dice) {
        this.dice = Arrays.asList(dice);
    }

    public void rollDice() {
        int sum = 0;
        for (ActivityDice diceItem : dice) {
            sum += diceItem.rollFor(activity);
        }
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
        cards.addAll(upstream.getCards(upstream.isPullable()));
    }

    public Predicate<Card> isPullable() {
        return p -> p.getRemainingWork(this.activity) == 0;
    }
}
