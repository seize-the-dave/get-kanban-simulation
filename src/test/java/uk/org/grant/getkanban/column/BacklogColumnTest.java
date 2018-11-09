package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.DaysFactory;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.card.Cards;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BacklogColumnTest {
    @Test
    public void testPull() {
        Card card = Cards.getCard("S1");
        Column backlog = new BacklogColumn();

        backlog.addCard(card);
        Optional<Card> actual = backlog.pull(new Context(null, null));

        assertThat(actual.get(), is(card));
    }

    @Test
    public void onSelectedUpdatesSelectedDate() {
        Card card = Cards.getCard("S33");
        card.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));

        assertThat(card.getDaySelected(), is(1));
    }
}
