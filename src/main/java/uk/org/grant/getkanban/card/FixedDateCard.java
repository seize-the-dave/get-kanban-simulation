package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.State;

public class FixedDateCard extends AbstractCard {
    private final int dueDate;
    private final int fine;
    private final int payment;
    private final int subscribers;

    public FixedDateCard(String name, Size size, int analysis, int development, int test, int subscribers, int dueDate, int fine, int payment) {
        super(name, size, analysis, development, test);

        this.subscribers = subscribers;
        this.fine = fine;
        this.payment = payment;
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
        if (hitDueDate()) {
            return subscribers;
        }
        return 0;
    }

    @Override
    public int getFineOrPayment() {
        if (hitDueDate()) {
            return payment;
        } else {
            return fine;
        }
    }

    private boolean hitDueDate() {
        return getDayDeployed() > 0 && getDayDeployed() <= getDueDate();
    }

    @Override
    public int getDueDate() {
        return this.dueDate;
    }
}
