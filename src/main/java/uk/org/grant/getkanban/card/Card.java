package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;

import java.math.BigDecimal;

public interface Card {
    String getName();
    StandardCard.Size getSize();
    int getRemainingWork(State state);
    void doWork(State state, int effort);
    int getRemainingWork();
    void onSelected(Context context);
    void onDeployed(Context context);
    void onReadyToDeploy(Context context);
    int getDaySelected();
    int getDayDeployed();
    int getSubscribers();
    int getCycleTime();
    int getFineOrPayment();
    int getDueDate();
    void setBlocker(Blocker b);
    Blocker getBlocker();
    boolean isBlocked();
    boolean isExpeditable(Day d);
    int getCostOfDelay(Day d);

    enum Size {
        VERY_HIGH(new BigDecimal(40)),
        HIGH(new BigDecimal(20)),
        MEDIUM(new BigDecimal(10)),
        LOW(new BigDecimal(5)),
        NONE(new BigDecimal(1));

        private final BigDecimal value;

        public BigDecimal getValue() {
            return value;
        }

        Size(BigDecimal value) {
            this.value = value;
        }
    }
}
