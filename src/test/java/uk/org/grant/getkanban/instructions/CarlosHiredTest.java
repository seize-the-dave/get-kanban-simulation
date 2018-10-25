package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.*;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CarlosHiredTest {
    @Test
    public void wipOnTestRemoved() {
        Board b = new Board();
        Column c = new StateColumn(State.TEST, 3, new NullColumn());
        b.setColumn(State.TEST, c);

        assertThat(((StateColumn) c).getLimit(), is(3));

        Day d = new Day(1, new CarlosHired(new AtomicInteger()));
        d.endOfDay(b);

        assertThat(((StateColumn) c).getLimit(), is(Integer.MAX_VALUE));
    }
}
