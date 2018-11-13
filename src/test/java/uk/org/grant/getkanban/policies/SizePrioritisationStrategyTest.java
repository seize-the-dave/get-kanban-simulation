package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.policies.SizePrioritisationStrategy;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SizePrioritisationStrategyTest {
    @Test
    public void shortItemsItemsRiseToTheTop() {
        Queue<Card> queue = new PriorityQueue<>(new SizePrioritisationStrategy());
        queue.add(Cards.getCard("S29"));
        queue.add(Cards.getCard("S28"));

        assertThat(queue.poll().getRemainingWork(), is(11));
        assertThat(queue.poll().getRemainingWork(), is(14));
    }
}
