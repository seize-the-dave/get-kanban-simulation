package uk.org.grant.getkanban.policies;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComplexDiceAssignmentStrategy implements DiceAssignmentStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplexDiceAssignmentStrategy.class);
    private final BigDecimal expectedSpecialistRoll;
    private final BigDecimal expectedAwayRoll;
    private final int maxDice;

    public ComplexDiceAssignmentStrategy() {
        this(new BigDecimal(3.5));
    }

    public ComplexDiceAssignmentStrategy(BigDecimal expectedRoll) {
        this(expectedRoll, 2);
    }

    public ComplexDiceAssignmentStrategy(BigDecimal expectedRoll, int maxDice) {
        this.expectedSpecialistRoll = expectedRoll;
        this.expectedAwayRoll = expectedRoll.divide(new BigDecimal(2.0));
        this.maxDice = maxDice;
    }

    /**
     * Assigns dice to cards on the board
     *
     * @param board
     */
    public void assignDice(Board board) {
        List<StateDice> jobSeekers = new ArrayList<>();
        List<StateDice> diceToAllocate = new LinkedList<>(board.getDice());
        Map<Card, DiceGroup> assignedCards = new HashMap<>();
        Multimap<State, DiceGroup> stateGroups = ArrayListMultimap.create();
        int maxDice = Math.min(this.maxDice, board.getDice().size());

        for (int i = maxDice; i <= board.getDice().size(); i++) {
            // Accidentally allocating four dice (over two groups) to E1
            for (State state : new State[]{State.TEST, State.DEVELOPMENT, State.ANALYSIS, State.TEST, State.DEVELOPMENT, State.ANALYSIS}) {
                if (diceToAllocate.isEmpty()) {
                    continue;
                }
                StateColumn column = board.getStateColumn(state);
                LOGGER.info("Assigning workers for the {} column", column);

                Predicate<StateDice> primary = d -> d.getActivity() == state;
                Predicate<StateDice> secondary = d -> d.getActivity() != state;

                // Get a list of all the incomplete cards in this column
                List<Card> incompleteCards = column.getIncompleteCards().stream().filter(c -> !c.isBlocked()).collect(Collectors.toList());

                // How many primary workers do we have in this column?
                Set<StateDice> allWorkers = diceToAllocate.stream().filter(primary).collect(Collectors.toSet());

                // We might have some workers who couldn't do anything in their primary column
                if (!jobSeekers.isEmpty()) {
                    // Move the secondary workers to our primary group
                    allWorkers.addAll(jobSeekers);
                    jobSeekers.clear();
                }

                // If we don't have any workers, there's not a lot we can do here.
                if (allWorkers.isEmpty()) {
                    continue;
                }

                // This column doesn't have any cards to work on, but hopefully our workers can be used elsewhere
                if (incompleteCards.isEmpty()) {
                    // Join the work queue
                    jobSeekers.addAll(allWorkers);

                    continue;
                }

                LOGGER.info("Putting {} to work against {} in {}", allWorkers, incompleteCards, column);

                for (Card c : incompleteCards) {
                    LOGGER.info("Trying to assign workers to {}", c);
                    // Make sure we still have some workers to allocate
                    if (allWorkers.isEmpty()) {
                        LOGGER.info("No more workers to allocate in {}", state);
                        continue;
                    }

                    // Let's check to see if this card has been assigned workers before
                    DiceGroup preassignedGroup = assignedCards.get(c);
                    if (preassignedGroup != null && preassignedGroup.getDice().size() == maxDice) {
                        // This card has already been assigned the maximum number of workers
                        LOGGER.info("{} is already in a full dice group {}", c, assignedCards.get(c));
                        continue;
                    } else if (preassignedGroup != null) {
                        // This card has been assigned workers before.  Let's see if we have any more
                        // workers to assign this time
                        LOGGER.info("Releasing {} from {} back into pool", preassignedGroup.getDice(), c);
                        allWorkers.addAll(preassignedGroup.getDice());
                    }

                    // Dice for whom this column is their primary skill (Developers in Development)
                    List<StateDice> specialists = allWorkers.stream().filter(primary).collect(Collectors.toList());
                    // Dice for whom this column is not their primary skill (Testers in Development)
                    List<StateDice> secondaries = allWorkers.stream().filter(secondary).collect(Collectors.toList());

                    // How much work is left for this card?
                    BigDecimal remainingWork = new BigDecimal(c.getRemainingWork(state));
                    LOGGER.info("{} has {} points of work remaining", c, remainingWork);

                    List<StateDice> allocatedDice = new ArrayList<>();
                    // How many dice can we assign?
                    int remainingDice = maxDice;

                    // Are there any primary workers to assign?
                    if (specialists.size() > 0) {
                        // How many dice do we need to complete the work
                        int requiredSpecialistDie = remainingWork.divide(expectedSpecialistRoll, RoundingMode.UP).intValue();
                        // But how many dice are we actually prepared to use?
                        int maxSpecialistDice = Math.min(Math.min(requiredSpecialistDie, specialists.size()), remainingDice);

                        // Reduce the total number of dice we're prepared to use
                        remainingDice -= maxSpecialistDice;

                        // How add the dice we're going to allocate
                        allocatedDice.addAll(allWorkers.stream().filter(primary).limit(maxSpecialistDice).collect(Collectors.toList()));

                        // How much work are we expecting to remove from from the card?
                        BigDecimal specialistWorkWeExpectToRemove = expectedSpecialistRoll.multiply(new BigDecimal(maxSpecialistDice));

                        // Remove the work we expect leave for secondaries
                        remainingWork = remainingWork.subtract(specialistWorkWeExpectToRemove);
                        LOGGER.info("We expect to remove {} points using specialist skills", specialistWorkWeExpectToRemove);
                    }

                    if (column.canAssignSecondaryWorkers() == false) {
                        LOGGER.info("Can't assign secondary workers for {}!", column);
                    }

                    // Check if we have work left for secondaries
                    if (column.canAssignSecondaryWorkers() && secondaries.size() > 0 && remainingWork.compareTo(BigDecimal.ZERO) == 1) {
                        // Remove the work
                        LOGGER.info("{} points of remaining work for secondary skills", remainingWork);

                        // How much (if any) workers are needed from secondary skills?
                        int requiredSecondaryDice = remainingWork.divide(expectedAwayRoll, RoundingMode.UP).intValue();

                        // How many secondary workers can we assign to this card?
                        int maxSecondaryDice = Math.min(Math.min(requiredSecondaryDice, secondaries.size()), remainingDice);

                        // Remove the work using the secondary workers
                        allocatedDice.addAll(allWorkers.stream().filter(secondary).limit(maxSecondaryDice).collect(Collectors.toList()));

                        BigDecimal secondaryWorkWeExpectToRemove = expectedAwayRoll.multiply(new BigDecimal(maxSecondaryDice));
                        LOGGER.info("We expect to remove {} points using secondary skills", secondaryWorkWeExpectToRemove);
                    }

                    // Create a new dice group for this card
                    if (preassignedGroup != null) {
                        // Reallocated dice back to the group
                        LOGGER.info("Using {} for existing group {}", allocatedDice, preassignedGroup);
                        preassignedGroup.setDice(allocatedDice);
                    } else if (allocatedDice.size() > 0) {
                        DiceGroup diceGroup = new DiceGroup(c, allocatedDice.toArray(new StateDice[0]));
                        assignedCards.put(c, diceGroup);
                        stateGroups.put(state, diceGroup);
                    }

                    // Remove the dice we just used in this column
                    allWorkers.removeAll(allocatedDice);

                    // Remove the dice from the board dice
                    diceToAllocate.removeAll(allocatedDice);
                }

                // We still have some unallocated workers for this column, so add them back into the general worker pool
                if (!allWorkers.isEmpty()) {
                    LOGGER.info("{} is unassigned in {}", allWorkers, state);
                    jobSeekers.addAll(allWorkers);
                }
            }
            for (State state : stateGroups.keySet()) {
                LOGGER.info("{} cards have been assigned dice in {}", stateGroups.get(state).size(), state);
                StateColumn column = board.getStateColumn(state);
                column.assignDice(stateGroups.get(state).toArray(new DiceGroup[0]));
            }
            // We failed to assign dice.  This is a failure.
            if (jobSeekers.size() > 0) {
                maxDice++;
                LOGGER.error("These dice are unused {}.  Increasing dice per card to {}", jobSeekers, maxDice);
            } else {
                break;
            }
        }
    }
}
