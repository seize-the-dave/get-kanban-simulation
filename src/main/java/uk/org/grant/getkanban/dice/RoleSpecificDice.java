package uk.org.grant.getkanban.dice;

import uk.org.grant.getkanban.Role;

public class RoleSpecificDice {
    private final Dice dice;
    private final Role role;

    public RoleSpecificDice(Role role, Dice dice) {
        this.role = role;
        this.dice = dice;
    }

    public int rollFor(Role role) {
        if (this.role == role) {
            return dice.roll();
        } else {
            return Long.valueOf(Math.round(Integer.valueOf(dice.roll()).doubleValue() / 2.0)).intValue();
        }
    }
}
