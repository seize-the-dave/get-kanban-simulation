package uk.org.grant.getkanban;

import uk.org.grant.getkanban.column.Visitable;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.instructions.Instruction;

public class Day implements Visitable<Context> {
    private final int ordinal;
    private final Instruction[] instructions;

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
        this(ordinal, new Instruction[]{});

    }

    public Day(int ordinal, Instruction... instructions) {
        this.ordinal = ordinal;
        this.instructions = instructions;
    }

    public void standUp(Board board) {
        board.getColumn(State.ANALYSIS).allocateDice(board.getDice(State.ANALYSIS).toArray(new StateDice[] {}));
        board.getColumn(State.DEVELOPMENT).allocateDice(board.getDice(State.DEVELOPMENT).toArray(new StateDice[] {}));
        board.getColumn(State.TEST).allocateDice(board.getDice(State.TEST).toArray(new StateDice[] {}));
    }

    public void visit(Context context) {
        for (int i = 0; i < 2; i++) {
            context.getBoard().getColumn(State.DEPLOY).visit(context);
            context.getBoard().getColumn(State.READY_TO_DEPLOY).visit(context);
            context.getBoard().getColumn(State.TEST).visit(context);
            context.getBoard().getColumn(State.DEVELOPMENT).visit(context);
            context.getBoard().getColumn(State.ANALYSIS).visit(context);
            context.getBoard().getColumn(State.SELECTED).visit(context);
            context.getBoard().getColumn(State.BACKLOG).visit(context);
        }
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void endOfDay(Board b) {
        for (Instruction instruction : instructions) {
            instruction.execute(b);
        }
    }

    @Override
    public String toString() {
        return "[Day " + ordinal + "]";
    }
}
