package uk.org.grant.getkanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.ReadyToDeployColumn;

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
            backlog.addCard(Cards.getCard("S29"));
            backlog.addCard(Cards.getCard("S30"));
            backlog.addCard(Cards.getCard("S31"));
            backlog.addCard(Cards.getCard("S32"));
            backlog.addCard(Cards.getCard("S33"));
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
    }

    @Override
    public int getSubscribers() {
        return getFineOrPayment();
    }

    @Override
    public String toString() {
        return getName();
    }
}
