package uk.org.grant.getkanban.card;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BlockerTest {
    @Test
    public void startsWith7RemainingWork() {
        assertThat(new Blocker().getRemainingWork(), is(7));
    }

    @Test
    public void doingWorkReducesRemainingWork() {
        Blocker b = new Blocker();
        b.doWork(5);

        assertThat(b.getRemainingWork(), is(2));
    }
}
