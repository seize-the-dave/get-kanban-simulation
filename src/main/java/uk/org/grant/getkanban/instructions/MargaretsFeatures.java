package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Cards;

public class MargaretsFeatures implements Instruction {
    @Override
    public void execute(Board b) {
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S19"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S20"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S21"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S22"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S23"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S24"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S25"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S26"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S27"));
        b.getColumn(State.BACKLOG).addCard(Cards.getCard("S28"));
    }
}
