package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.Arrays;
import java.util.List;

public class Board {
    private final List<ActivityDice> dice;

    public Board(ActivityDice... dice) {
        this.dice = Arrays.asList(dice);
    }

    public List<ActivityDice> getDice() {
        return dice;
    }
}
