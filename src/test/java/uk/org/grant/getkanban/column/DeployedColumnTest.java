package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeployedColumnTest {
    @Test
    public void marksDeployedDayOnPull() {
        Card card = Cards.getCard("S10");
        Column backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.visit(new Context(new Board(), new Day(1)));

        Column deployed = new DeployedColumn(selected);
        selected.visit(new Context(new Board(), new Day(2)));

        assertThat(card.getDayDeployed(), is(2));
    }
}
