package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.Column;

public class MargaretsFeatures implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(MargaretsFeatures.class);
    @Override
    public void execute(Board b) {
        LOGGER.info("Margaret from marketing has suggested some more features");
        Column backlog = b.getOptions();
        for (String name : new String[] {"S19", "S20", "S21", "S22", "S23", "S24", "S25", "S26", "S27", "S28"}) {
            Card card = Cards.getCard(name);
            LOGGER.info("{} -> {}", card, backlog);
            backlog.addCard(card);
        }
    }
}
