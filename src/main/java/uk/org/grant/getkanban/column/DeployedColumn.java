package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;

import java.util.*;

public class DeployedColumn extends FifoColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployedColumn.class);
    private Column expedite;

    public DeployedColumn(Column upstream, Column expedite) {
        super(upstream);

        this.expedite = expedite;
    }

    @Override
    public void doTheWork(Context context) {
        doTheWork(context, expedite, ClassOfService.EXPEDITE);
        doTheWork(context, standard, ClassOfService.STANDARD);
    }

    private void doTheWork(Context context, Column upstream, ClassOfService cos) {
        while (true) {
            Optional<Card> optionalCard = upstream.pull(context, cos);
            if (optionalCard.isPresent() == false) {
                LOGGER.warn("{}: {} has nothing available to pull ({})", context.getDay(), upstream, cos);
                break;
            } else {
                optionalCard.get().onDeployed(context);
                addCard(optionalCard.get(), cos);
                LOGGER.info("{}: {} -> {} -> {} ({})", context.getDay(), upstream, optionalCard.get().getName(), this, cos);
            }
        }
    }

    @Override
    public String toString() {
        return "[DEPLOYED (" + getCards().size() + "/∞)";
    }

    public void clear() {
        cards.clear();
    }
}
