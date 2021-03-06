package uk.org.grant.getkanban.dice;

import uk.org.grant.getkanban.State;

public class StateDice {
    private final Dice dice;
    private final State state;

    public StateDice(State state, Dice dice) {
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

    @Override
    public String toString() {
        return this.state.toString().substring(0, 1);
    }
}
