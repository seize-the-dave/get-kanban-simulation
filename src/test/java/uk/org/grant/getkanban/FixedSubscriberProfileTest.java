package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FixedSubscriberProfileTest {
    @Test
    public void shouldAlwaysReturnZero() {
        SubscriberProfile profile = new FixedSubscriberProfile();
        assertThat(0, is(profile.getSubscribers(1)));
        assertThat(0, is(profile.getSubscribers(15)));
    }

    @Test
    public void shouldAlwaysReturnValue() {
        SubscriberProfile profile = new FixedSubscriberProfile(6);
        assertThat(6, is(profile.getSubscribers(1)));
        assertThat(6, is(profile.getSubscribers(15)));
    }
}
