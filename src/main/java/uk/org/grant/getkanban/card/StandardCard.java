package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.*;

import java.util.Objects;

public class StandardCard extends AbstractCard {
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
