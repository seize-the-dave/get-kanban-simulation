package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.BacklogColumn;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class GraduateGlenExpediteTest {
    @Test
    public void ticketE2ShouldBeAddedToBacklog() {
        Instruction bigCorp = new GraduateGlenExpedite();

        Board b = new Board();
        b.setColumn(State.BACKLOG, new BacklogColumn());

        assertThat(b.getColumn(State.BACKLOG).getCards(), empty());

        bigCorp.execute(b);

        assertThat(b.getColumn(State.BACKLOG).getCards(), contains(Cards.getCard("E2")));
    }
}
