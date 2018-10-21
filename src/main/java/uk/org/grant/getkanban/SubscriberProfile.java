package uk.org.grant.getkanban;

public class SubscriberProfile {
    private final int[] subscribers;

    public SubscriberProfile(int[] subscribers) {
        this.subscribers = subscribers;
    }

    public int getSubscribers(int cycleTime) {
        try {
            return subscribers[cycleTime];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }
}
