package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Cards;

public class MargaretsFeatures implements Instruction {
    @Override
    public void execute(Board b) {
        b.getBacklog().addCard(Cards.getCard("S19"));
        b.getBacklog().addCard(Cards.getCard("S20"));
        b.getBacklog().addCard(Cards.getCard("S21"));
        b.getBacklog().addCard(Cards.getCard("S22"));
        b.getBacklog().addCard(Cards.getCard("S23"));
        b.getBacklog().addCard(Cards.getCard("S24"));
        b.getBacklog().addCard(Cards.getCard("S25"));
        b.getBacklog().addCard(Cards.getCard("S26"));
        b.getBacklog().addCard(Cards.getCard("S27"));
        b.getBacklog().addCard(Cards.getCard("S28"));
    }
}
