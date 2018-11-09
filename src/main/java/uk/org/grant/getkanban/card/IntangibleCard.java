package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;

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
    }

    @Override
    public int getSubscribers() {
        return 0;
    }
}
