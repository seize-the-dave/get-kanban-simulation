package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.policies.DiceAssignmentStrategy;

import java.util.Comparator;

public class RunConfiguration {
    private final Comparator<Card> backlogComparator;
    private final Comparator<Card> activityComparator;
    private final DiceAssignmentStrategy diceAssignmentStrategy;
    private final boolean supportsTraining;
    private final WipLimitAdjustment[] wipLimitAdjustments;

    public RunConfiguration(Comparator<Card> backlogComparator, Comparator<Card> activityComparator, DiceAssignmentStrategy diceAssignmentStrategy, boolean supportsTraining, WipLimitAdjustment... wipLimitAdjustments) {
        this.backlogComparator = backlogComparator;
        this.activityComparator = activityComparator;
        this.diceAssignmentStrategy = diceAssignmentStrategy;
        this.supportsTraining = supportsTraining;
        this.wipLimitAdjustments = wipLimitAdjustments;
    }

    public Comparator<Card> getBacklogComparator() {
        return backlogComparator;
    }

    public Comparator<Card> getActivityComparator() {
        return activityComparator;
    }

    public DiceAssignmentStrategy getDiceAssignmentStrategy() {
        return diceAssignmentStrategy;
    }

    public boolean supportsTraining() {
        return supportsTraining;
    }

    public WipLimitAdjustment[] getWipLimitAdjustments() {
        return wipLimitAdjustments;
    }
}
