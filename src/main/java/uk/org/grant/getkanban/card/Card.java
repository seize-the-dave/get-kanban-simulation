package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;

public interface Card {
    String getName();
    void onSelected(Context context);
    void onDeployed(Context context);
}
