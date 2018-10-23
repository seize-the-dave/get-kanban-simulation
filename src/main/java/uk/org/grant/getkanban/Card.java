package uk.org.grant.getkanban;

import java.util.EnumMap;
import java.util.Map;

public class Card {
    public static final Card S1 = new Card(Size.SMALL, 1, 1, 1, new SubscriberProfile(new int[] {1, 2, 3}));
    public static final Card S2 = new Card(Size.SMALL, 5, 2, 1, new SubscriberProfile(new int[] {1, 2, 3}));

    private final Size size;
    private final SubscriberProfile profile;
    private final Map<Activity, Integer> work = new EnumMap<>(Activity.class);
    private int start;
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
        return finish - start;
    }

    private void checkIsFinished() {
        if (start == 0 || finish == 0) {
            throw new IllegalStateException();
        }
    }

    public void setStartDay(int start) {
        this.start = start;
    }

    public void setFinishDay(int finish) {
        if (this.start == 0) {
            throw new IllegalStateException();
        }
        if (finish < this.start) {
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

    public enum Size {
        SMALL
    }
}
