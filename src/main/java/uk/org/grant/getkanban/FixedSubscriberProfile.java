package uk.org.grant.getkanban;

import java.util.stream.IntStream;

public class FixedSubscriberProfile extends SubscriberProfile {
    public FixedSubscriberProfile() {
        this(0);
    }

    public FixedSubscriberProfile(int value) {
        super(IntStream.generate(() -> value).limit(15).toArray());
    }
}
