package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.column.Column;

public class IntangibleCard extends AbstractCard {
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
            Column backlog = context.getBoard().getBacklog();
            backlog.addCard(Cards.getCard("S29"));
            backlog.addCard(Cards.getCard("S30"));
            backlog.addCard(Cards.getCard("S31"));
            backlog.addCard(Cards.getCard("S32"));
            backlog.addCard(Cards.getCard("S33"));
        }
    }

    @Override
    public int getSubscribers() {
        return 0;
    }
}
