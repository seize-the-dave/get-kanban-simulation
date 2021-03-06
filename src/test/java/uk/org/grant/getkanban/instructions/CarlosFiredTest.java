package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.NullColumn;
import uk.org.grant.getkanban.column.StateColumn;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CarlosFiredTest {
    @Test
    public void wipLimitsShouldBeRestored() {
        Board b = new Board();

        assertThat(b.getStateColumn(State.TEST).getLimit(), is(3));

        Day day1 = new Day(1, new CarlosHired());
        day1.endOfDay(b);

        assertThat(b.getStateColumn(State.TEST).getLimit(), is(Integer.MAX_VALUE));

        Day day2 = new Day(2, new CarlosFired());
        day2.endOfDay(b);

        assertThat(b.getStateColumn(State.TEST).getLimit(), is(3));
    }

    @Test
    public void anotherTesterShouldBeHired() {
        Board b = new Board();
        b.clear();

        assertThat(b.getDice(State.TEST).size(), is(0));

        Day d = new Day(1, new CarlosFired());
        d.endOfDay(b);

        assertThat(b.getDice(State.TEST).size(), is(1));
    }
}
