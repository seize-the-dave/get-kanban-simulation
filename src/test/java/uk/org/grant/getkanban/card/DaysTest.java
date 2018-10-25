package uk.org.grant.getkanban.card;

import org.junit.Assert;
import org.junit.Test;
import uk.org.grant.getkanban.Days;

import static org.hamcrest.Matchers.is;

public class DaysTest {
    @Test
    public void getDayNineReturnsDayWithOrdinalOfNine() {
        Assert.assertThat(Days.getDay(9).getOrdinal(), is(9));
    }
}
