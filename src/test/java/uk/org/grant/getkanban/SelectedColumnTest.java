package uk.org.grant.getkanban;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SelectedColumnTest {
    @Test
    public void marksSelectedDayOnPull() {
        Card card = CardFactory.getCard("S10");
        BacklogColumn backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.visit(new Day(1));

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

        column.addCard(CardFactory.getCard("S1"));
        column.addCard(CardFactory.getCard("S2"));
    }

    @Test
    public void willNotPullBeyondWipLimit() {
        Column backlog = new BacklogColumn();
        SelectedColumn selected = new SelectedColumn(1, backlog);

        backlog.addCard(CardFactory.getCard("S1"));
        selected.addCard(CardFactory.getCard("S2"));

        selected.visit(new Day(1));
        assertThat(selected.getCards().size(), is(1));
    }
}
