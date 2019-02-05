package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Day;

public class ExpediteCard extends FixedDateCard {
    public ExpediteCard(String name, Size size, int analysis, int development, int test, int subscribers, int dueDate, int fine, int payment) {
        super(name, size, analysis, development, test, subscribers, dueDate, fine, payment);
    }

    @Override
    public boolean isExpeditable(Day d) {
        return true;
    }
}
