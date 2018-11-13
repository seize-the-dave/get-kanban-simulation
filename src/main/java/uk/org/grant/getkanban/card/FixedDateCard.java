package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;

public class FixedDateCard extends AbstractCard {
    private final int dueDate;
    private final int subscribers;

    public FixedDateCard(String name, Size size, int analysis, int development, int test, int subscribers, int dueDate) {
        super(name, size, analysis, development, test);

        this.subscribers = subscribers;
        this.dueDate = dueDate;
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
        if ((getDayDeployed() > 0 || getDayDeployed() <= this.getDueDate())) {
            return subscribers;
        }
        return 0;
    }

    @Override
    public int getFineOrPayment() {
        if (getName().equals("F1") && (getDayDeployed() == 0 || getDayDeployed() > 15)) {
            return -1500;
        }
        return 0;
    }

    @Override
    public int getDueDate() {
        return this.dueDate;
    }
}
