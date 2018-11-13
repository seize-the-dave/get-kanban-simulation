package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.card.IntangibleCard;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntangiblesFirstPrioritsationStrategyTest {
    @Test
    public void intangiblesRiseToTheTop() {
        Queue<Card> queue = new PriorityQueue<>(new IntangiblesFirstPrioritisationStrategy());
        queue.add(Cards.getCard("S1"));
        queue.add(Cards.getCard("S2"));
        queue.add(Cards.getCard("I1"));
        queue.add(Cards.getCard("S3"));
        queue.add(Cards.getCard("S4"));

        assertThat(queue.poll(), is(instanceOf(IntangibleCard.class)));
        assertThat(queue.poll(), is(instanceOf(StandardCard.class)));
    }
}
