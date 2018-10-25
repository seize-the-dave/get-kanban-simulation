package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WipAgingPrioritisationStrategyTest {
    @Test
    public void olderItemsShouldBeFirst() {
        Queue<Card> queue = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
        queue.add(Cards.getCard("S8"));
        queue.add(Cards.getCard("S3"));

        assertThat(queue.poll().getDaySelected(), is(2));
        assertThat(queue.poll().getDaySelected(), is(6));
    }
}
