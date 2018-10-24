package uk.org.grant.getkanban;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class DaysTest {
    @Test
    public void getDayNineReturnsDayWithOrdinalOfNine() {
        Assert.assertThat(Days.getDay(9).getOrdinal(), is(9));
    }
}
