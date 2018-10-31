package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ContextTest {
    @Test
    public void shouldAccessBoard() {
        Board b = new Board();
        Day day = new DaysFactory(true).getDay(1);

        Context ctx = new Context(b, day);
        assertThat(ctx.getBoard(), is(b));
    }

    @Test
    public void shouldAccessDay() {
        Board b = new Board();
        Day day = new DaysFactory(true).getDay(1);

        Context ctx = new Context(b, day);
        assertThat(ctx.getDay(), is(day));
    }
}
