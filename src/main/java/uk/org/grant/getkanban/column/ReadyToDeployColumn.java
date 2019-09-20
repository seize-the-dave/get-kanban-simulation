package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ReadyToDeployColumn extends UnbufferedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyToDeployColumn.class);
    private final MutablePriorityQueue<Card> cards = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
    private int deploymentFrequency = 3;

    public ReadyToDeployColumn(Column upstream) {
        super(upstream);
    }


    @Override
    public void addCard(Card card, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            throw new IllegalArgumentException("Expedite is not applicable for ready to deploy");
        }
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return cards.stream().sorted(cards.getComparator()).collect(Collectors.toList());
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            throw new IllegalArgumentException("Shouldn't pull from ready to deploy with expedite class of service");
        }
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
            Optional<Card> optionalCard = standard.pull(context, ClassOfService.STANDARD);
            if (optionalCard.isPresent()) {
                optionalCard.get().onReadyToDeploy(context);
                addCard(optionalCard.get(), ClassOfService.STANDARD);
                LOGGER.info("{}: {} -> {} -> {}", context.getDay(), standard, optionalCard.get().getName(), this);
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[READY TO DEPLOY (" + cards.size() + "/∞)]";
    }

    public void setDeploymentFrequency(int deploymentFrequency) {
        LOGGER.info("Now deploying every {} day(s)", deploymentFrequency);
        this.deploymentFrequency = deploymentFrequency;
    }

    public int getDeploymentFrequency() {
        return this.deploymentFrequency;
    }

    public void clear() {
        cards.clear();
    }
}
