package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.card.Cards;

import java.util.*;

public class DeployedColumn extends UnbufferedColumn {

    public DeployedColumn(Column upstream) {
        super(upstream);
    }

    @Override
    public void visit(Context context) {
        // TODO: Deploy Set 3 for I3
        // TODO: Automate deployments for I2
//        System.out.println("In " + this + " on " + context.getDay());
        while (true) {
//            System.out.println("Try pulling from " + upstream);
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                optionalCard.get().setDayDeployed(context.getDay().getOrdinal());
                addCard(optionalCard.get());

                if (optionalCard.get().getName().equals("I3")) {
                    Column backlog = context.getBoard().getColumn(State.BACKLOG);
                    backlog.addCard(Cards.getCard("S29"));
                    backlog.addCard(Cards.getCard("S30"));
                    backlog.addCard(Cards.getCard("S31"));
                    backlog.addCard(Cards.getCard("S32"));
                    backlog.addCard(Cards.getCard("S33"));
                }
//                System.out.println("Pulled " + optionalCard.get() + " into " + this);
            } else {
//                System.out.println(upstream + " has nothing to pull.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[DEPLOYED (" + getCards().size() + "/-)";
    }
}
