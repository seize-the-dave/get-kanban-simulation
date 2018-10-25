package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.*;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CarlosFiredTest {
    @Test
    public void wipLimitsShouldBeRestored() {
        AtomicInteger wipStore = new AtomicInteger();

        Board b = new Board();
        Column c = new ActivityColumn(State.TEST, 3, new NullColumn());
        b.setColumn(State.TEST, c);

        assertThat(((ActivityColumn) c).getLimit(), is(3));

        Day day1 = new Day(1, new CarlosHired(wipStore));
        day1.endOfDay(b);

        assertThat(((ActivityColumn) c).getLimit(), is(Integer.MAX_VALUE));

        Day day2 = new Day(2, new CarlosFired(wipStore));
        day2.endOfDay(b);

        assertThat(((ActivityColumn) c).getLimit(), is(3));
    }

    @Test
    public void anotherTesterShouldBeHired() {
        Board b = new Board();
        b.setColumn(State.TEST, new ActivityColumn(State.TEST, 3, new NullColumn()));

        assertThat(b.getDice(State.TEST).size(), is(0));

        Day d = new Day(1, new CarlosFired(new AtomicInteger()));
        d.endOfDay(b);

        assertThat(b.getDice(State.TEST).size(), is(1));
    }
}
