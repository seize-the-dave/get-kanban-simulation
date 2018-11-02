package uk.org.grant.getkanban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.column.Workable;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.instructions.Instruction;

import java.util.Optional;

public class Day implements Workable<Context> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day.class);
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
        replenishSelected(board);
        assignDice(board);
    }

    private void replenishSelected(Board board) {
        board.getSelected().doTheWork(new Context(board, this));
    }

    private void assignDice(Board board) {
        for (State state : new State[] {State.ANALYSIS, State.DEVELOPMENT, State.TEST}) {
            StateColumn column = board.getStateColumn(state);
            Optional<Card> card = column.getCards().stream().filter(c -> c.getRemainingWork(state) != 0).findFirst();
            if (card.isPresent()) {
                DiceGroup group = new DiceGroup(card.get(), board.getDice(state).toArray(new StateDice[0]));
                column.assignDice(group);
            } else {
                LOGGER.warn("Can't assign dice for {}, as no items are available!", state);
            }
        }
    }

    public void doTheWork(Context context) {
        for (int i = 0; i < 2; i++) {
            context.getBoard().getDeployed().doTheWork(context);
            context.getBoard().getReadyToDeploy().doTheWork(context);
            context.getBoard().getStateColumn(State.TEST).doTheWork(context);
            context.getBoard().getStateColumn(State.DEVELOPMENT).doTheWork(context);
            context.getBoard().getStateColumn(State.ANALYSIS).doTheWork(context);
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
        return "D" + ordinal;
    }
}
