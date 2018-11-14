package uk.org.grant.getkanban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.column.Workable;
import uk.org.grant.getkanban.instructions.Instruction;
import uk.org.grant.getkanban.policies.DiceAssignmentStrategy;

public class Day implements Workable<Context> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day.class);
    private final int ordinal;
    private final Instruction[] instructions;
    private final DiceAssignmentStrategy diceAssignmentStrategy = new DiceAssignmentStrategy();

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
        LOGGER.info("{}: STAND UP", this);
//        if (ordinal == 14) {
//            board.getSelected().setLimit(1);
//            board.getStateColumn(State.ANALYSIS).setLimit(1);
//            board.getStateColumn(State.DEVELOPMENT).setLimit(2);
//            board.getStateColumn(State.TEST).setLimit(2);
//
//        }
        replenishSelected(board);
        assignDice(board);
    }

    private void replenishSelected(Board board) {
        LOGGER.info("{}: Replenish Selected Column", this);
        board.getSelected().doTheWork(new Context(board, this));
    }

    private void assignDice(Board board) {
        LOGGER.info("{}: Assigning Dice to Tickets", this);
        diceAssignmentStrategy.assignDice(board);
    }

    public void doTheWork(Context context) {
        LOGGER.info("{}: DO THE WORK", this);
        context.getBoard().getDeployed().doTheWork(context);
        context.getBoard().getReadyToDeploy().doTheWork(context);
        context.getBoard().getStateColumn(State.TEST).doTheWork(context);
        context.getBoard().getStateColumn(State.DEVELOPMENT).doTheWork(context);
        context.getBoard().getStateColumn(State.ANALYSIS).doTheWork(context);
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void endOfDay(Board b) {
        LOGGER.info("{}: END OF DAY", this);
        if (instructions.length == 0) {
            LOGGER.info("{}: No special instructions", this);
        }
        for (Instruction instruction : instructions) {
            instruction.execute(b);
        }
    }

    @Override
    public String toString() {
        return "D" + ordinal;
    }

}
