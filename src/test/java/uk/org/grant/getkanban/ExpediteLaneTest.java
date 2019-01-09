package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.Options;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExpediteLaneTest {
    @Test
    public void neverPullStandardCards() {
        Options options = new Options();
        Card s12 = Cards.getCard("S12");
        options.addCard(s12);

        Swimlane expedite = new ExpediteLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), empty());
    }

    @Test
    public void neverPullIntangileCards() {
        Options options = new Options();
        Card i3 = Cards.getCard("I3");
        options.addCard(i3);

        Swimlane expedite = new ExpediteLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), empty());
    }

    @Test
    public void canPullExpediteCards() {
        Options options = new Options();
        Card e1 = Cards.getCard("E1");
        options.addCard(e1);

        Swimlane expedite = new ExpediteLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), contains(e1));
    }

    @Test
    public void canNotPullFixedDateCardsDueInThreeDaysOrMore() {
        Options options = new Options();
        Card f1 = Cards.getCard("F1");
        options.addCard(f1);

        Swimlane expedite = new ExpediteLane(options);
        expedite.pull(new Day(10));

        assertThat(expedite.getCards(), empty());
    }

    @Test
    public void canPullFixedDateCardsDueInLessThanThreeDays() {
        Options options = new Options();
        Card f1 = Cards.getCard("F1");
        options.addCard(f1);

        Swimlane expedite = new ExpediteLane(options);
        expedite.pull(new Day(14));

        assertThat(expedite.getCards(), contains(f1));
    }

    @Test
    public void pullsToWorkInProgressLimit() {
        Options options = new Options();
        Card e1 = Cards.getCard("E1");
        Card e2 = Cards.getCard("E2");
        Card f1 = Cards.getCard("F1");
        options.addCard(e1);
        options.addCard(e2);
        options.addCard(f1);

        Swimlane expedite = new ExpediteLane(options);
        expedite.setWipLimit(2);
        expedite.pull(new Day(14));

        assertThat(expedite.getCards().size(), is(expedite.getWipLimit()));
        assertThat(expedite.getCards(), contains(e1, e2));
    }
}
