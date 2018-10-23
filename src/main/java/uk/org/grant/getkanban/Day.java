package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

public class Day implements Visitable<Board> {
    private final int ordinal;

    // 9 Billing
    // 10 Add Blocker to S10.  Pink Pete Dice (7 Work)
    // 11 Hire Carlos.  Only Testers on Test Items; No Testers on anything else; Ignore WIP
    // 12 Billing.  Ask for Set 2.
    // 13 .
    // 14 Carlos Fired.  Add Tester. Enable WIP Limits
    // 15 Introduce E1
    // 16 .
    // 17 Defect on first S ticket in Test (or next one to arrive in Test).  Erase Test effort.  Work on small blue ticket.  Send Ted on training? (A3; D6)
    // 18 Introduce Tammy if Ted went on training.  Introduce E2 to backlog.
    // 19 .
    // 20 .
    // 21 .
    public Day(int ordinal) {
        this.ordinal = ordinal;
    }
    
    public void standUp(Board board) {
        Column analysis = board.getColumn(Column.Type.ANALYSIS);
        analysis.allocateDice(board.getDice().toArray(new ActivityDice[] {}));
    }

    public void visit(Board board) {
        ActivityColumn column = (ActivityColumn) board.getColumn(Column.Type.ANALYSIS);
        column.visit(this);
    }

    public int getOrdinal() {
        return ordinal;
    }
}
