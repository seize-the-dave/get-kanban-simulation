package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;

public class ExpediteCard extends StandardCard {
    public ExpediteCard(String name, Size size, int analysis, int development, int test) {
        super(name, size, analysis, development, test, new FixedSubscriberProfile());
    }

    public ExpediteCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile) {
        super(name, size, analysis, development, test, profile);
    }

    public ExpediteCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int daySelected) {
        super(name, size, analysis, development, test, profile, daySelected);
    }

    public ExpediteCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int daySelected, int dayDeployed) {
        super(name, size, analysis, development, test, profile, daySelected, dayDeployed);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExpediteCard == false) {
            return false;
        }
        return this.getName().equals(((ExpediteCard) o).getName());
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
