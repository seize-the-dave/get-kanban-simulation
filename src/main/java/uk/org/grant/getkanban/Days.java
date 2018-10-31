package uk.org.grant.getkanban;

import uk.org.grant.getkanban.instructions.BigCorpExpedite;
import uk.org.grant.getkanban.instructions.CarlosFired;
import uk.org.grant.getkanban.instructions.CarlosHired;

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
            default:
                return new Day(day);
        }
    }
}
