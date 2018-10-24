package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeployedColumnTest {
    @Test
    public void marksDeployedDayOnPull() {
        Card card = new Card("S1", Card.Size.HIGH, 1, 1, 1, new SubscriberProfile(new int[]{}));
        Column backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(backlog);
        selected.visit(new Day(1));

        Column deployed = new DeployedColumn(selected);
        deployed.visit(new Day(2));

        assertThat(card.getDayDeployed(), is(2));
    }
}
