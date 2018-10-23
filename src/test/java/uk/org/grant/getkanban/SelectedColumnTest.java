package uk.org.grant.getkanban;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SelectedColumnTest {
    @Test
    public void marksSelectedDayOnPull() {
        Card card = new Card(Card.Size.HIGH, 1, 1, 1, new SubscriberProfile(new int[]{}));
        Column backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(backlog);
        selected.pullFromUpstream(1);

        assertThat(card.getDaySelected(), is(1));
    }
}
