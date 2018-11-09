package uk.org.grant.getkanban;

public class FixedSubscriberProfile implements SubscriberProfile {
    private final int subscribers;

    public FixedSubscriberProfile() {
        this(0);
    }

    public FixedSubscriberProfile(int subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public int getSubscribers(int cycleTime) {
        return subscribers;
    }
}
