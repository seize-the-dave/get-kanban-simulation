package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

public class Day {
    public void standUp(Board board) {
        Column analysis = board.getColumn(Column.Type.ANALYSIS);
        analysis.allocateDice(board.getDice().toArray(new ActivityDice[] {}));
    }
}
