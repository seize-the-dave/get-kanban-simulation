package uk.org.grant.getkanban.column;

import org.junit.Assert;
import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;

import static org.hamcrest.Matchers.is;

public class ReadyToDeployColumnTest {
    @Test
    public void testIsOnlyPullableOnBillingDays() {
        Column selected = new SelectedColumn(1, new NullColumn());
        selected.addCard(Cards.getCard("S1"));

        Context context = new Context(new Board(), new Day(1));

        Column readyToDeploy = new ReadyToDeployColumn(selected);
        readyToDeploy.doTheWork(context);
        Assert.assertThat(readyToDeploy.pull(context).isPresent(), is(false));

        context = new Context(new Board(), new Day(3));

        readyToDeploy.doTheWork(context);
        Assert.assertThat(readyToDeploy.pull(context).isPresent(), is(true));
    }
}
