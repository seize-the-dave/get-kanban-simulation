package uk.org.grant.getkanban.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComplexDiceAssignmentStrategy implements DiceAssignmentStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplexDiceAssignmentStrategy.class);
    private final BigDecimal expectedHomeRoll;
    private final BigDecimal expectedAwayRoll;
    private final int maxDice;

    public ComplexDiceAssignmentStrategy() {
        this(new BigDecimal(3.5));
    }

    public ComplexDiceAssignmentStrategy(BigDecimal expectedRoll) {
        this(expectedRoll, 2);
    }

    public ComplexDiceAssignmentStrategy(BigDecimal expectedRoll, int maxDice) {
        this.expectedHomeRoll = expectedRoll;
        this.expectedAwayRoll = expectedRoll.divide(new BigDecimal(2.0));
        this.maxDice = maxDice;
    }

    /**
     * Assigns dice to cards on the board
     *
     * @param board
     */
    public void assignDice(Board board) {
        List<StateDice> unassignedDice = new ArrayList<>();

        List<StateDice> diceToAllocate = new LinkedList<>(board.getDice());

        for (State state : new State[]{State.TEST, State.DEVELOPMENT, State.ANALYSIS, State.TEST, State.DEVELOPMENT}) {
            LOGGER.info("Assigning for {}", state);
            StateColumn column = board.getStateColumn(state);

            Predicate<StateDice> home = d -> d.getActivity() == state;
            Predicate<StateDice> away = d -> d.getActivity() != state;

            List<StateDice> diceToAllocateInColumn = diceToAllocate.stream().filter(home).collect(Collectors.toList());

            // No cards to allocate to, so take these dice and pass them on
            if (column.getIncompleteCards().stream().filter(c11 -> !c11.isBlocked()).count() == 0) {
                if (!diceToAllocateInColumn.isEmpty()) {
                    LOGGER.info("{} is unassigned in {}", diceToAllocateInColumn, state);
                    unassignedDice.addAll(diceToAllocateInColumn);
                }
                continue;
            }

            // We have some assigned dice carried over, so add them.
            if (!unassignedDice.isEmpty()) {
                LOGGER.info("Adding left over dice {} to {}", unassignedDice, diceToAllocateInColumn);
                diceToAllocateInColumn.addAll(unassignedDice);
                unassignedDice.clear();
            }

            LOGGER.info("Assigning {} to cards in {}", diceToAllocateInColumn, column);
            List<DiceGroup> groups = new ArrayList<DiceGroup>();
            column.getIncompleteCards().stream().filter(c1 -> !c1.isBlocked()).forEach(c -> {
                if (diceToAllocateInColumn.size() > 0) {
                    List<StateDice> homeDice = diceToAllocateInColumn.stream().filter(home).collect(Collectors.toList());
                    List<StateDice> awayDice = diceToAllocateInColumn.stream().filter(away).collect(Collectors.toList());

                    BigDecimal remainingWork = new BigDecimal(c.getRemainingWork(state));

                    LOGGER.info("Remaining work: {}", remainingWork);
                    int neededHomeDice = remainingWork.divide(expectedHomeRoll, RoundingMode.UP).intValue();
                    int maxHomeDice = Math.min(Math.min(neededHomeDice, homeDice.size()), maxDice);
                    List<StateDice> allocatedDice = diceToAllocateInColumn.stream().filter(home).limit(maxHomeDice).collect(Collectors.toList());
                    BigDecimal workWeExpectToRemove = expectedHomeRoll.multiply(new BigDecimal(maxHomeDice));

                    LOGGER.info("Removed home work {}", workWeExpectToRemove);
                    if (workWeExpectToRemove.compareTo(remainingWork) == -1) {
                        remainingWork = remainingWork.subtract(workWeExpectToRemove);
                        LOGGER.info("Remaining away work {}", remainingWork);
                        int neededAwayDice = remainingWork.divide(expectedAwayRoll, RoundingMode.UP).intValue();
                        int maxAwayDice = Math.min(Math.min(neededAwayDice, awayDice.size()), this.maxDice - maxHomeDice);
                        allocatedDice.addAll(diceToAllocateInColumn.stream().filter(away).limit(maxAwayDice).collect(Collectors.toList()));
                    }

                    groups.add(new DiceGroup(c, allocatedDice.toArray(new StateDice[0])));

                    LOGGER.info("{}", diceToAllocateInColumn);
                    LOGGER.info("{}", allocatedDice);
                    diceToAllocateInColumn.removeAll(allocatedDice);
                    diceToAllocate.removeAll(allocatedDice);
                    LOGGER.info("{}", diceToAllocateInColumn);
                }
            });
            if (groups.size() > 0) {
                column.assignDice(groups.toArray(new DiceGroup[0]));
            }

            if (!diceToAllocateInColumn.isEmpty()) {
                LOGGER.info("{} is unassigned in {}", diceToAllocateInColumn, state);
                unassignedDice.addAll(diceToAllocateInColumn);
            }
        }
        if (unassignedDice.size() > 0) {
            LOGGER.error("Unused dice: {}", unassignedDice);
        }
    }
}
