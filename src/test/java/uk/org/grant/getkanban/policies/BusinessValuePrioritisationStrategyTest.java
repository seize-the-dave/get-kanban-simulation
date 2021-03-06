package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BusinessValuePrioritisationStrategyTest {
    @Test
    public void highValueItemsRiseToTheTop() {
        Queue<Card> queue = new PriorityQueue<>(new BusinessValuePrioritisationStrategy());
        queue.add(Cards.getCard("S2"));
        queue.add(Cards.getCard("S3"));
        queue.add(Cards.getCard("S4"));

        assertThat(queue.poll().getSize(), is(Card.Size.HIGH));
        assertThat(queue.poll().getSize(), is(Card.Size.MEDIUM));
        assertThat(queue.poll().getSize(), is(Card.Size.LOW));
    }
}
