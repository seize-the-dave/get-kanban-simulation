package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.Options;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class StandardLaneTest {
    @Test
    public void neverPullExpediteCards() {
        Options options = new Options();
        Card e1 = Cards.getCard("E1");
        options.addCard(e1);

        Swimlane expedite = new StandardLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), empty());
    }

    @Test
    public void canPullStandardCards() {
        Options options = new Options();
        Card s11 = Cards.getCard("S11");
        options.addCard(s11);

        Swimlane expedite = new StandardLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), contains(s11));
    }
}
