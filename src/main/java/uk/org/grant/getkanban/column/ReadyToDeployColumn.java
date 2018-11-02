package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;

import java.util.*;

public class ReadyToDeployColumn extends AbstractColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyToDeployColumn.class);
    private final Queue<Card> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private final Column upstream;

    public ReadyToDeployColumn(Column upstream) {
        this.upstream = upstream;
    }


    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context) {
        doTheWork(context);
        if (context.getDay().getOrdinal() % 3 == 0) {
            return Optional.ofNullable(cards.poll());
        } else {
            return Optional.empty();
        }

    }

    @Override
    public void doTheWork(Context context) {
        // TODO: Automate regression for I2
        LOGGER.info("In " + this + " on " + context.getDay());
        while (true) {
            LOGGER.info("Pull from " + upstream);
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
                LOGGER.info("Pulled " + optionalCard.get() + " into " + this);
            } else {
                LOGGER.info(upstream + " has nothing to pull.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[READY TO DEPLOY (" + cards.size() + "/∞)]";
    }
}
