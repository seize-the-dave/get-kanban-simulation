package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Cards;

public class GraduateGlenExpedite implements Instruction {
    @Override
    public void execute(Board b) {
        b.getBacklog().addCard(Cards.getCard("E2"));
    }
}
