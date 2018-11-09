package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.card.Cards;

import java.util.*;

public class DeployedColumn extends UnbufferedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployedColumn.class);

    public DeployedColumn(Column upstream) {
        super(upstream);
    }

    @Override
    public void doTheWork(Context context) {
        LOGGER.info("{}: Doing work in {} ", context.getDay(), this);
        while (true) {
            LOGGER.info("Pull from " + upstream);
            Optional<StandardCard> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent() == false) {
                LOGGER.warn("{} has nothing available to pull", upstream);
                break;
            } else {
                optionalCard.get().onDeployed(context);
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
            }
        }
    }

    @Override
    public String toString() {
        return "[DEPLOYED (" + getCards().size() + "/∞)";
    }
}
