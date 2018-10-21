package uk.org.grant.getkanban.dice;

import uk.org.grant.getkanban.Activity;

public class ActivityDice {
    private final Dice dice;
    private final Activity activity;

    public ActivityDice(Activity activity, Dice dice) {
        this.activity = activity;
        this.dice = dice;
    }

    public int rollFor(Activity activity) {
        if (this.activity == activity) {
            return dice.roll();
        } else {
            return Long.valueOf(Math.round(Integer.valueOf(dice.roll()).doubleValue() / 2.0)).intValue();
        }
    }
}
