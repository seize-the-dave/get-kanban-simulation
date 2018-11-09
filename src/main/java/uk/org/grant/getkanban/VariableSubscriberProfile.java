package uk.org.grant.getkanban;

public class VariableSubscriberProfile implements SubscriberProfile {
    private final int[] subscribers;

    public VariableSubscriberProfile(int[] subscribers) {
        if (subscribers.length != 15) {
            throw new IllegalArgumentException("Expected 15 values, got " + subscribers.length);
        }
        this.subscribers = subscribers;
    }

    @Override
    public int getSubscribers(int cycleTime) {
        try {
            return subscribers[Math.min(cycleTime, 15) - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No subscribers for cycle time " + cycleTime);
        }
    }
}
