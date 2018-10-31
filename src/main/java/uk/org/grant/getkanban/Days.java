package uk.org.grant.getkanban;

import uk.org.grant.getkanban.instructions.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Days {
    public static Day getDay(int day) {
        AtomicInteger testWipStore = new AtomicInteger();
        switch (day) {
            case 11:
                return new Day(day, new CarlosHired(testWipStore));
            case 14:
                return new Day(day, new CarlosFired(testWipStore));
            case 15:
                return new Day(day, new BigCorpExpedite());
            case 17:
                return new Day(day, new TedsTrainingOpportunity(true));
            case 18:
                return new Day(day, new GraduateGlenExpedite());
            default:
                return new Day(day);
        }
    }
}
