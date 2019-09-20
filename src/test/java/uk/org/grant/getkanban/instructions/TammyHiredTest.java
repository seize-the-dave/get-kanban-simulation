package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.DaysFactory;
import uk.org.grant.getkanban.State;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TammyHiredTest {
    @Test
    public void tammyShouldBeHiredAndTedShouldBeBack() {
        Board b = new Board();

        assertThat(b.getDice(State.TEST).size(), is(2));

        DaysFactory daysFactory = new DaysFactory(true);
        Day d = daysFactory.getDay(18);
        d.endOfDay(b);

        assertThat(b.getDice(State.TEST).size(), is(4));
    }

    @Test
    public void tammyNotHiredAndTedShouldBeBack() {
        Board b = new Board();

        assertThat(b.getDice(State.TEST).size(), is(2));

        DaysFactory daysFactory = new DaysFactory(false);
        Day d = daysFactory.getDay(18);
        d.endOfDay(b);

        assertThat(b.getDice(State.TEST).size(), is(2));
    }
}
