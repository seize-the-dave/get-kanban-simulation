package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MutablePriorityQueueTest {
    @Test
    public void canChangeComparator() {
        MutablePriorityQueue<Card> queue = new MutablePriorityQueue<>(new BusinessValuePrioritisationStrategy());
        queue.add(Cards.getCard("S5"));
        queue.add(Cards.getCard("S10"));

        assertThat(queue.peek().getName(), is("S10"));

        queue.setComparator(new WipAgingPrioritisationStrategy());

        assertThat(queue.peek().getName(), is("S5"));
    }
}
