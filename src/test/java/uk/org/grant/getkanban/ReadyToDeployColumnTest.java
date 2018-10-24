package uk.org.grant.getkanban;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class ReadyToDeployColumnTest {
    @Test
    public void testIsOnlyPullableOnBillingDays() {
        Column selected = new SelectedColumn(1, new NullColumn());
        selected.addCard(CardFactory.getCard("S1"));

        Column readyToDeploy = new ReadyToDeployColumn(selected);
        readyToDeploy.visit(new Day(1));
        Assert.assertThat(readyToDeploy.pull().isPresent(), is(false));

        readyToDeploy.visit(new Day(3));
        Assert.assertThat(readyToDeploy.pull().isPresent(), is(true));
    }
}
