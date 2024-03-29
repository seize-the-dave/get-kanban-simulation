package uk.org.grant.getkanban;

import uk.org.grant.getkanban.instructions.*;
import uk.org.grant.getkanban.policies.DiceAssignmentStrategy;
import uk.org.grant.getkanban.policies.NoCrossSkillingDiceAssignmentStrategy;

public class DaysFactory {
    private final boolean training;
    private final DiceAssignmentStrategy diceAssignmentStrategy;

    public DaysFactory(boolean training) {
        this(training, new NoCrossSkillingDiceAssignmentStrategy());
    }

    public DaysFactory(boolean training, DiceAssignmentStrategy diceAssignmentStrategy) {
        this.training = training;
        this.diceAssignmentStrategy = diceAssignmentStrategy;
    }

    public Day getDay(int day) {
        switch (day) {
            case 10:
                return new Day(day, diceAssignmentStrategy, new PeteFromPlatformTeam());
            case 11:
                return new Day(day, diceAssignmentStrategy, new CarlosHired());
            case 12:
                return new Day(day, diceAssignmentStrategy, new MargaretsFeatures());
            case 14:
                return new Day(day, diceAssignmentStrategy, new CarlosFired());
            case 15:
                return new Day(day, diceAssignmentStrategy, new BigCorpExpedite());
            case 17:
                return new Day(day, diceAssignmentStrategy, new TedsTrainingOpportunity(training), new DefectFound());
            case 18:
                return new Day(day, diceAssignmentStrategy, new GraduateGlenExpedite(), new TammyHired(training));
            default:
                return new Day(day, diceAssignmentStrategy);
        }
    }
}
