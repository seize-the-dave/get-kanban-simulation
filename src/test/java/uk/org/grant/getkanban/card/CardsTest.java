package uk.org.grant.getkanban.card;

import org.junit.Test;

public class CardsTest {
    @Test(expected = IllegalArgumentException.class)
    public void missingCardShouldThrowException() {
        Cards.getCard("AA");
    }
}
