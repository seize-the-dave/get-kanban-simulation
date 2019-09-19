package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeployedColumnTest {
    @Test
    public void marksDeployedDayOnPull() {
        Card card = Cards.getCard("S10");
        Column backlog = new Options();
        backlog.addCard(card, ClassOfService.STANDARD);

        Column selected = new SelectedColumn(1, backlog);
        selected.doTheWork(new Context(new Board(), new Day(1)));

        Column deployed = new DeployedColumn(selected, new NullColumn());
        deployed.doTheWork(new Context(new Board(), new Day(2)));

        assertThat(card.getDayDeployed(), is(2));
    }
}
