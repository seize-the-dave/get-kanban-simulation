package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.*;

public class StandardCard extends AbstractCard {
    private final SubscriberProfile profile;

    public StandardCard(String name, Size size, int analysis, int development, int test) {
        this(name, size, analysis, development, test, new FixedSubscriberProfile());
    }

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
        return profile.getSubscribers(getCycleTime());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StandardCard == false) {
            return false;
        }
        return this.getName().equals(((StandardCard) o).getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return getName() + "[" + getRemainingWork(State.ANALYSIS) + "/" + getRemainingWork(State.DEVELOPMENT) + "/" + getRemainingWork(State.TEST) + "]" ;
    }
}
