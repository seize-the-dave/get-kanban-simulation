package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;

public class FixedDateCard extends AbstractCard {
    public FixedDateCard(String name, Size size, int analysis, int development, int test) {
        super(name, size, analysis, development, test);
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof FixedDateCard == false) {
            return false;
        }
        return this.getName().equals(((FixedDateCard) o).getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return getName() + "[" + getRemainingWork(State.ANALYSIS) + "/" + getRemainingWork(State.DEVELOPMENT) + "/" + getRemainingWork(State.TEST) + "]" ;
    }

    @Override
    public int getSubscribers() {
        return 0;
    }
}
