package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.BacklogColumn;

public class BigCorpExpedite implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(BigCorpExpedite.class);

    @Override
    public void execute(Board b) {
        LOGGER.info("BigCorp has asked us to expedite E1");

        BacklogColumn backlog = b.getBacklog();
        Card e1 = Cards.getCard("E1");
        backlog.addCard(e1);
        LOGGER.info("{} -> {}", e1, backlog);
    }
}
