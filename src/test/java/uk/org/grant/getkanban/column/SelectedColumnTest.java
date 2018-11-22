package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SelectedColumnTest {
    @Test
    public void marksSelectedDayOnPull() {
        Card card = Cards.getCard("S10");
        Options backlog = new Options();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.doTheWork(new Context(new Board(), new Day(1)));

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
        Column backlog = new Options();
        SelectedColumn selected = new SelectedColumn(1, backlog);

        backlog.addCard(Cards.getCard("S1"));
        selected.addCard(Cards.getCard("S2"));

        selected.doTheWork(new Context(new Board(), new Day(1)));
        assertThat(selected.getCards().size(), is(1));
    }

    @Test
    public void canChangePriority() {
        Column deployed = new SelectedColumn(2, new NullColumn());
        deployed.addCard(Cards.getCard("S10"));
        deployed.addCard(Cards.getCard("S5"));

        assertThat(deployed.getCards().peek().getName(), is("S5"));

        deployed.orderBy(new BusinessValuePrioritisationStrategy());

        assertThat(deployed.getCards().peek().getName(), is("S10"));
    }
}
