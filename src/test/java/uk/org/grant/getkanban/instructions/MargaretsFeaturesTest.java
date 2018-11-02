package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.BacklogColumn;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MargaretsFeaturesTest {
    @Test
    public void shouldIntroduceSet2() {
        Board b = new Board();

        DaysFactory days = new DaysFactory(true);
        Day day = days.getDay(12);

        day.endOfDay(b);

        assertThat(b.getBacklog().getCards().size(), is(10));
    }
}
