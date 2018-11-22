package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.Options;

public class GraduateGlenExpedite implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraduateGlenExpedite.class);
    @Override
    public void execute(Board b) {
        Options backlog = b.getOptions();
        Card e2 = Cards.getCard("E2");
        backlog.addCard(e2);
        LOGGER.info("{} -> {}", e2, backlog);
    }

    @Override
    public String toString() {
        return "Glen the Graduate has suggested E2";
    }
}
