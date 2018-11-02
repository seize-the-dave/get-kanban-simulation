package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;

import java.util.*;

public class DeployedColumn extends UnbufferedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployedColumn.class);

    public DeployedColumn(Column upstream) {
        super(upstream);
    }

    @Override
    public void doTheWork(Context context) {
        // TODO: Deploy Set 3 for I3
        // TODO: Automate deployments for I2
        LOGGER.info("In " + this + " on " + context.getDay());
        while (true) {
            LOGGER.info("Try pulling from " + upstream);
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                optionalCard.get().setDayDeployed(context.getDay().getOrdinal());
                addCard(optionalCard.get());

                if (optionalCard.get().getName().equals("I3")) {
                    Column backlog = context.getBoard().getBacklog();
                    backlog.addCard(Cards.getCard("S29"));
                    backlog.addCard(Cards.getCard("S30"));
                    backlog.addCard(Cards.getCard("S31"));
                    backlog.addCard(Cards.getCard("S32"));
                    backlog.addCard(Cards.getCard("S33"));
                }
                LOGGER.info("Pulled {} into {} from {}", optionalCard.get(), this, upstream);
            } else {
                LOGGER.warn("Nothing to pull.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[DEPLOYED (" + getCards().size() + "/∞)";
    }
}
