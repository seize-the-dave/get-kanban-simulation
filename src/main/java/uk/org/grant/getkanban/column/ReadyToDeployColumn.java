package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;

import java.util.*;

public class ReadyToDeployColumn extends UnbufferedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyToDeployColumn.class);
    private final Queue<Card> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private int deploymentFrequency = 3;

    public ReadyToDeployColumn(Column upstream) {
        super(upstream);
    }


    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Queue<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context) {
        doTheWork(context);
        if (context.getDay().getOrdinal() % deploymentFrequency == 0) {
            return Optional.ofNullable(cards.poll());
        } else {
            return Optional.empty();
        }

    }

    @Override
    public void doTheWork(Context context) {
        while (true) {
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                optionalCard.get().onReadyToDeploy(context);
                addCard(optionalCard.get());
                LOGGER.info("{}: {} -> {} -> {}", context.getDay(), upstream, optionalCard.get().getName(), this);
            } else {
                LOGGER.warn("{}: {} has nothing available to pull", context.getDay(), upstream);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[READY TO DEPLOY (" + cards.size() + "/∞)]";
    }

    public void setDeploymentFrequency(int deploymentFrequency) {
        this.deploymentFrequency = deploymentFrequency;
    }
}
