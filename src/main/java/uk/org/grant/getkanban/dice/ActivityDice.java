package uk.org.grant.getkanban.dice;

import uk.org.grant.getkanban.State;

public class ActivityDice {
    private final Dice dice;
    private final State state;

    public ActivityDice(State state, Dice dice) {
        this.state = state;
        this.dice = dice;
    }

    public State getActivity() {
        return this.state;
    }

    public int rollFor(State state) {
        if (this.state == state) {
            return dice.roll();
        } else {
            return Long.valueOf(Math.round(Integer.valueOf(dice.roll()).doubleValue() / 2.0)).intValue();
        }
    }
}
