package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SubscriberProfileTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCannotFindSubscribersForInvalidCycleTime() {
        SubscriberProfile profile = new SubscriberProfile(new int[] {});
        profile.getSubscribers(1);
    }

    @Test
    public void testSubscribers() {
        SubscriberProfile profile = new SubscriberProfile(new int[] {20, 10});
        assertThat(20, is(profile.getSubscribers(1)));
    }
}
