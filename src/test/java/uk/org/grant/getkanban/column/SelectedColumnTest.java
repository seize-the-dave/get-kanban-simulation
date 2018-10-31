package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SelectedColumnTest {
    @Test
    public void marksSelectedDayOnPull() {
        Card card = Cards.getCard("S10");
        BacklogColumn backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.visit(new Context(new Board(), new Day(1)));

        assertThat(card.getDaySelected(), is(1));
    }

    @Test
    public void canGetWipLimit() {
        SelectedColumn column = new SelectedColumn(4, new NullColumn());

        assertThat(column.getLimit(), is(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotExceedWipLimit() {
        SelectedColumn column = new SelectedColumn(1, new NullColumn());

        column.addCard(Cards.getCard("S1"));
        column.addCard(Cards.getCard("S2"));
    }

    @Test
    public void willNotPullBeyondWipLimit() {
        Column backlog = new BacklogColumn();
        SelectedColumn selected = new SelectedColumn(1, backlog);

        backlog.addCard(Cards.getCard("S1"));
        selected.addCard(Cards.getCard("S2"));

        selected.visit(new Context(new Board(), new Day(1)));
        assertThat(selected.getCards().size(), is(1));
    }
}
