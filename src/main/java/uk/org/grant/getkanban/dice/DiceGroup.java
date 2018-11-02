package uk.org.grant.getkanban.dice;

import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DiceGroup {
    private final AtomicBoolean rolled = new AtomicBoolean();
    private final StateDice[] die;
    private final Card card;
    private final AtomicInteger dots = new AtomicInteger(0);
    private State originalState;

    public DiceGroup(Card card, StateDice... die) {
        this.card = card;
        this.die = die;
    }

    public void rollFor(State state) {
        if (rolled.getAndSet(true)) {
            throw new IllegalStateException("Already rolled");
        }
        this.originalState = state;
        for (StateDice dice : die) {
            dots.getAndAdd(dice.rollFor(state));
        }
        spendPoints(state, card);
        // If more than two dice are assigned to a single ticket, any leftover points must be disregarded
        if (die.length > 2) {
            dots.set(0);
        }
    }

    public int getLeftoverPoints() {
        return dots.get();
    }

    public void spendLeftoverPoints(State state, Card card) {
        if (state != originalState) {
            throw new IllegalStateException("Points must be spent in same specialisation die was originally rolled for");
        }
        spendPoints(state, card);
    }

    private void spendPoints(State state, Card card) {
        int delta = Math.min(dots.get(), card.getRemainingWork(state));

        card.doWork(state, delta);
        dots.getAndAdd(-delta);
    }
}
