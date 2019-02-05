package uk.org.grant.getkanban.column;

import org.junit.Assert;
import org.junit.Test;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.dice.StateDice;

import static org.hamcrest.Matchers.is;

public class ReadyToDeployColumnTest {
    @Test
    public void testIsOnlyPullableOnBillingDays() {
        Column selected = new SelectedColumn(1, new NullColumn());
        selected.addCard(Cards.getCard("S1"), ClassOfService.STANDARD);

        Context context = new Context(new Board(), new Day(1));

        Column readyToDeploy = new ReadyToDeployColumn(selected);
        readyToDeploy.doTheWork(context);
        Assert.assertThat(readyToDeploy.pull(context, ClassOfService.STANDARD).isPresent(), is(false));

        context = new Context(new Board(), new Day(3));

        readyToDeploy.doTheWork(context);
        Assert.assertThat(readyToDeploy.pull(context, ClassOfService.STANDARD).isPresent(), is(true));
    }

    @Test
    public void canPullEverydayAfterEnablingContinuousDelivery() {
        Card i1 = Cards.getCard("I1");

        Board b = new Board();
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(1)));
        b.addDice(new StateDice(State.DEVELOPMENT, new LoadedDice(4)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(2)));
        b.getOptions().addCard(i1, ClassOfService.STANDARD);

        DaysFactory df = new DaysFactory(true);
        for (int i = 4; i < 7; i++) {
            Day d = df.getDay(i);
            d.standUp(b);
            d.doTheWork(new Context(b, d));
            d.endOfDay(b);
        }

        Assert.assertThat(i1.getDayDeployed(), is(6));
    }
}
