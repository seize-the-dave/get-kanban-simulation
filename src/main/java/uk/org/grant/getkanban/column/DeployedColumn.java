package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
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
        while (true) {
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent() == false) {
                LOGGER.warn("{}: {} has nothing available to pull", context.getDay(), upstream);
                break;
            } else {
                optionalCard.get().onDeployed(context);
                addCard(optionalCard.get());
                LOGGER.info("{}: {} -> {} -> {}", context.getDay(), upstream, optionalCard.get().getName(), this);
            }
        }
    }

    @Override
    public String toString() {
        return "[DEPLOYED (" + getCards().size() + "/∞)";
    }
}
