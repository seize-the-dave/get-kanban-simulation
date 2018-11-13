package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;

public class ExpediteCard extends FixedDateCard {
    public ExpediteCard(String name, Size size, int analysis, int development, int test, int subscribers, int dueDate, int fine, int payment) {
        super(name, size, analysis, development, test, subscribers, dueDate, fine, payment);
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
