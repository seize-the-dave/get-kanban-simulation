package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.*;

public class ReadyToDeployColumn extends UnbufferedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyToDeployColumn.class);
    private final Queue<StandardCard> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());

    public ReadyToDeployColumn(Column upstream) {
        super(upstream);
    }


    @Override
    public void addCard(StandardCard card) {
        cards.add(card);
    }

    @Override
    public Queue<StandardCard> getCards() {
        return cards;
    }

    @Override
    public Optional<StandardCard> pull(Context context) {
        doTheWork(context);
        if (context.getDay().getOrdinal() % 3 == 0) {
            return Optional.ofNullable(cards.poll());
        } else {
            return Optional.empty();
        }

    }

    @Override
    public void doTheWork(Context context) {
        LOGGER.info("{}: Doing work in {} ", context.getDay(), this);
        while (true) {
            LOGGER.info("Pull from " + upstream);
            Optional<StandardCard> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
                LOGGER.info("Pulled " + optionalCard.get() + " into " + this);
            } else {
                LOGGER.warn("{} has nothing available to pull", upstream);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[READY TO DEPLOY (" + cards.size() + "/∞)]";
    }
}
