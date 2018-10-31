package uk.org.grant.getkanban;

import uk.org.grant.getkanban.instructions.*;

import java.util.concurrent.atomic.AtomicInteger;

public class DaysFactory {
    private final boolean training;
    private final AtomicInteger testWipStore;

    public DaysFactory(boolean training) {
        this.training = training;
        testWipStore = new AtomicInteger();
    }

    public Day getDay(int day) {
        switch (day) {
            case 11:
                return new Day(day, new CarlosHired(testWipStore));
            case 14:
                return new Day(day, new CarlosFired(testWipStore));
            case 15:
                return new Day(day, new BigCorpExpedite());
            case 17:
                return new Day(day, new TedsTrainingOpportunity(training));
            case 18:
                return new Day(day, new GraduateGlenExpedite(), new TammyHired(training));
            default:
                return new Day(day);
        }
    }
}
