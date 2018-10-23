package uk.org.grant.getkanban;

import java.util.EnumMap;
import java.util.Map;

public class Card {
    // I1 1; 4; 2 (Don't wait in ready to deploy)
    // I2 2; 2; 5 (2 points off test)
    // I3 1; 3; 3 (Set 3 of cards)
    // E1 4; 6; 6 Financial summary
    // E2 2; 3; 4 Lose 6 subs if delivered
    // F1 4; 3; 6 Financial summary
    // F2 + 30 Subscribers
    // S1
    public static final Card S1 = new Card(Size.LOW, 1, 1, 1, new SubscriberProfile(new int[] {1, 2, 3}));
    public static final Card S2 = new Card(Size.LOW, 5, 2, 1, new SubscriberProfile(new int[] {1, 2, 3}));

    private final Size size;
    private final SubscriberProfile profile;
    private final Map<Activity, Integer> work = new EnumMap<>(Activity.class);
    private int daySelected;
    private int finish;

    public Card(Size size, int analysis, int development, int test, SubscriberProfile profile) {
        this.size = size;
        this.work.put(Activity.ANALYSIS, analysis);
        this.work.put(Activity.DEVELOPMENT, development);
        this.work.put(Activity.TEST, test);
        this.profile = profile;
    }

    public Size getSize() {
        return size;
    }

    public int getSubscribers() {
        return profile.getSubscribers(getCycleTime());
    }

    public int getCycleTime() {
        checkIsFinished();
        return finish - daySelected;
    }

    private void checkIsFinished() {
        if (daySelected == 0 || finish == 0) {
            throw new IllegalStateException();
        }
    }

    public void setDaySelected(int start) {
        this.daySelected = start;
    }

    public void setFinishDay(int finish) {
        if (this.daySelected == 0) {
            throw new IllegalStateException();
        }
        if (finish < this.daySelected) {
            throw new IllegalStateException();
        }
        this.finish = finish;
    }

    public int getRemainingWork(Activity activity) {
        return work.get(activity);
    }

    public void doWork(Activity activity, int effort) {
        int remaining = getRemainingWork(activity);
        if (effort > remaining) {
            throw new IllegalArgumentException();
        }
        work.put(activity, remaining - effort);
    }

    public int getDaySelected() {
        return daySelected;
    }

    public enum Size {
        MEDIUM, HIGH, LOW
    }
}
