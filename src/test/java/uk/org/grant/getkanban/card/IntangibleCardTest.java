package uk.org.grant.getkanban.card;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntangibleCardTest {
    @Test
    public void noSubscribers() {
        assertThat(Cards.getCard("I3").getSubscribers(), is(0));
    }

    @Test
    public void databaseImprovementsAddFeaturesToBacklog() {
        Board b = new Board();
        Card i3 = Cards.getCard("I3");
        i3.onSelected(new Context(b, new Day(1)));

        i3.onDeployed(new Context(b, new Day(2)));

        assertThat(b.getOptions().getCards().poll(), is(Cards.getCard("S29")));
    }

    @Test
    public void deploymentAutomationReducesDeploymentFrequency() {
        Board b = new Board();
        Card i1 = Cards.getCard("I1");
        i1.onSelected(new Context(b, new Day(1)));
        assertThat(b.getReadyToDeploy().getDeploymentFrequency(), is(3));

        i1.onReadyToDeploy(new Context(b, new Day(2)));

        assertThat(b.getReadyToDeploy().getDeploymentFrequency(), is(1));
    }

    @Test
    public void testAutomationReducesTestEffortByTwoPoints() {
        Board b = new Board();
        Card i2 = Cards.getCard("I2");
        Card s3 = Cards.getCard("S3");
        Card s5 = Cards.getCard("S5");
        i2.onSelected(new Context(b, new Day(1)));
        b.getStateColumn(State.TEST).addCard(s3);
        assertThat(s3.getRemainingWork(State.TEST), is(6));
        assertThat(s5.getRemainingWork(State.TEST), is(9));

        i2.onReadyToDeploy(new Context(b, new Day(2)));
        b.getStateColumn(State.TEST).addCard(s5);

        assertThat(s3.getRemainingWork(State.TEST), is(4));
        assertThat(s5.getRemainingWork(State.TEST), is(7));
    }
}
