package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.NullColumn;
import uk.org.grant.getkanban.column.StateColumn;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CarlosHiredTest {
    @Test
    public void wipOnTestRemoved() {
        Board b = new Board();
        Column c = new StateColumn(State.TEST, 3, new NullColumn(), new NullColumn());

        assertThat(b.getStateColumn(State.TEST).getLimit(), is(3));

        Day d = new Day(1, new CarlosHired());
        d.endOfDay(b);

        assertThat(b.getStateColumn(State.TEST).getLimit(), is(Integer.MAX_VALUE));
    }
}
