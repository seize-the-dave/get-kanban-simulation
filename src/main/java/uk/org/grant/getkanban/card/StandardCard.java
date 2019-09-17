package uk.org.grant.getkanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.DeployedColumn;

import java.util.Objects;

public class StandardCard extends AbstractCard {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardCard.class);
    private final SubscriberProfile profile;

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile) {
        super(name, size, analysis, development, test);

        this.profile = profile;
    }

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int daySelected) {
        super(name, size, analysis, development, test, daySelected);

        this.profile = profile;
    }

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int daySelected, int dayDeployed) {
        super(name, size, analysis, development, test, daySelected, dayDeployed);

        this.profile = profile;
    }

    public int getSubscribers() {
        if (getDayDeployed() == 0) {
            return 0;
        }
        return profile.getSubscribers(getCycleTime());
    }

    @Override
    public int getCostOfDelay(Day d) {
        int daySelected = getDaySelected();
        int dayDeployed = Math.max(daySelected + 3, d.getOrdinal());
        if (daySelected == 0) {
            daySelected = d.getOrdinal();
            dayDeployed = daySelected + 3;
        }
        int cycleTime = dayDeployed - daySelected;

        int delay = 7;

        int stdRevenue = getRevenue(cycleTime, dayDeployed);
        int delayedRevenue = getRevenue(cycleTime + delay, dayDeployed + delay);

        return stdRevenue - delayedRevenue;
    }

    private int getRevenue(int cycleTime, int dayDeployed) {
        return profile.getSubscribers(cycleTime) * FinancialSummary.getRevenueMultiplier(FinancialSummary.getBillingDay(dayDeployed));
    }

    @Override
    public void onDeployed(Context context) {
        super.onDeployed(context);
        if (getCycleTime() == 0) {
            throw new IllegalStateException(this + ":" + getDayDeployed() + ":" + getDaySelected());
        }
        LOGGER.info("{}: Lead Time = {}d", this.getName(), this.getDayDeployed() - this.getDaySelected());
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof StandardCard == false) {
            return false;
        } else {
            StandardCard other = (StandardCard) o;
            return Objects.equals(getName(), other.getName());
        }
    }

    @Override
    public final int hashCode() {
        if (getName() == null) {
            return 0;
        }
        return getName().hashCode();
    }
}
