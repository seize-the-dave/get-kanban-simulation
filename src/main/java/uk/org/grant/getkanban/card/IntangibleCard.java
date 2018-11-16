package uk.org.grant.getkanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.ReadyToDeployColumn;
import uk.org.grant.getkanban.column.StateColumn;

public class IntangibleCard extends AbstractCard {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntangibleCard.class);

    public IntangibleCard(String name, StandardCard.Size size, int analysis, int development, int test) {
        super(name, size, analysis, development, test);
    }

    @Override
    public void onSelected(Context context) {
        super.onSelected(context);
    }

    @Override
    public void onDeployed(Context context) {
        super.onDeployed(context);

        if (getName().equals("I3")) {
            LOGGER.info("{}: Upgrade database version", context.getDay());
            Column backlog = context.getBoard().getBacklog();
            for (String name : new String[] {"S29", "S30", "S31", "S32", "S33"}) {
                Card card = Cards.getCard(name);
                LOGGER.info("{} -> {}", card, backlog);
                backlog.addCard(card);
            }
        }
    }

    @Override
    public void onReadyToDeploy(Context context) {
        super.onReadyToDeploy(context);

        if (getName().equals("I1")) {
            LOGGER.info("{}: Automate deployments", context.getDay());
            ReadyToDeployColumn readyToDeploy = context.getBoard().getReadyToDeploy();
            readyToDeploy.setDeploymentFrequency(1);
        }
        if (getName().equals("I2")) {
            LOGGER.info("{}: Automate testing", context.getDay());
            StateColumn test = context.getBoard().getStateColumn(State.TEST);
            test.getIncompleteCards().forEach(c -> {
                c.doWork(State.TEST, Math.min(2, c.getRemainingWork(State.TEST)));
                LOGGER.info("Test Automation: Removed 2 points of test effort from {}", c);
            });
            test.addListener(c -> {
                c.doWork(State.TEST, 2);
                LOGGER.info("Test Automation: Removed 2 points of test effort from {}", c);
            });
        }
    }

    @Override
    public int getSubscribers() {
        return 0;
    }
}
