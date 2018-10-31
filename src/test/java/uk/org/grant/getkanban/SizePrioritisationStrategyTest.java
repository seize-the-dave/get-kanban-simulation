package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SizePrioritisationStrategyTest {
    @Test
    public void shortItemsItemsRiseToTheTop() {
        Queue<Card> queue = new PriorityQueue<>(new SizePrioritisationStrategy());
        queue.add(Cards.getCard("S2"));
        queue.add(Cards.getCard("S4"));

        assertThat(queue.poll().getRemainingWork(), is(2));
        assertThat(queue.poll().getRemainingWork(), is(4));
    }
}
