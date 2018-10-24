package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeployedColumnTest {
    @Test
    public void marksDeployedDayOnPull() {
        Card card = CardFactory.getCard("S10");
        Column backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.visit(new Day(1));

        Column deployed = new DeployedColumn(selected);
        deployed.visit(new Day(2));

        assertThat(card.getDayDeployed(), is(2));
    }
}
