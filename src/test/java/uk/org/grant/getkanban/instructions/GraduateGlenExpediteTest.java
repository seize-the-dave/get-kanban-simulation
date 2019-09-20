package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Cards;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class GraduateGlenExpediteTest {
    @Test
    public void ticketE2ShouldBeAddedToBacklog() {
        Instruction bigCorp = new GraduateGlenExpedite();

        Board b = new Board();
        b.clear();

        assertThat(b.getOptions().getCards(), empty());

        bigCorp.execute(b);

        assertThat(b.getOptions().getCards(), contains(Cards.getCard("E2")));
    }
}
