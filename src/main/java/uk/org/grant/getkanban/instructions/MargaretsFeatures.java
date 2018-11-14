package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Cards;

public class MargaretsFeatures implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(MargaretsFeatures.class);
    @Override
    public void execute(Board b) {
        LOGGER.info("Margaret from marketing has suggested some more features");

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
