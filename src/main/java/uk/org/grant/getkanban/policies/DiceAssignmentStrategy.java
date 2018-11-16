package uk.org.grant.getkanban.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DiceAssignmentStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiceAssignmentStrategy.class);
    private final BigDecimal expectedRoll;
    private final int maxDice;

    public DiceAssignmentStrategy() {
        this(new BigDecimal(3.5));
    }

    public DiceAssignmentStrategy(BigDecimal expectedRoll) {
        this(expectedRoll, 2);
    }

    public DiceAssignmentStrategy(BigDecimal expectedRoll, int maxDice) {
        this.expectedRoll = expectedRoll;
        this.maxDice = maxDice;
    }

    /**
     * Assigns dice to cards on the board
     *
     * @param board
     */
    public void assignDice(Board board) {
        List<StateDice> unallocatedDice = new ArrayList<>();

        for (State state : new State[]{State.TEST, State.DEVELOPMENT, State.ANALYSIS}) {
            StateColumn column = board.getStateColumn(state);

            List<StateDice> dice = board.getDice(state);
            if (!unallocatedDice.isEmpty()) {
                LOGGER.info("Adding left over dice {} to {}", unallocatedDice, dice);
                dice.addAll(unallocatedDice);
                unallocatedDice.clear();
            }

            LOGGER.info("Ready to allocate {} to {}", dice, column);
            List<DiceGroup> groups = new ArrayList<DiceGroup>();
            column.getIncompleteCards().stream().filter(c -> c.isBlocked() == false).forEach(c -> {
                if (dice.size() > 0) {
                    int diceToAllocate = Math.min(Math.min(new BigDecimal(c.getRemainingWork(state)).divide(expectedRoll, RoundingMode.UP).intValue(), dice.size()), maxDice);
                    List<StateDice> allocatedDice = dice.subList(0, diceToAllocate);
                    groups.add(new DiceGroup(c, allocatedDice.toArray(new StateDice[0])));

                    dice.removeAll(allocatedDice);
                }
            });
            column.assignDice(groups.toArray(new DiceGroup[0]));

            if (!dice.isEmpty()) {
                LOGGER.info("{} left over", dice);
                unallocatedDice = dice;
            }
        }
    }
}
