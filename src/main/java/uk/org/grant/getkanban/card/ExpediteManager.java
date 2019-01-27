package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Day;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ExpediteManager {
    private Queue<Card> expedited;

    public ExpediteManager() {
        this.expedited = new ArrayBlockingQueue<>(1);
    }

    public boolean expedite(Card card, Day day) {
        if (card instanceof FixedDateCard && card.getName().startsWith("E")) {
            return this.expedited.offer(card);
        }
        if (card instanceof FixedDateCard && card.getDueDate() - day.getOrdinal() < 3) {
            return this.expedited.offer(card);
        }
        return false;
    }

    public boolean isExpedited(Card card) {
        return this.expedited.contains(card);
    }

    public void setWipLimit(int limit) {
        expedited = new ArrayBlockingQueue<>(limit, false, expedited);
    }

    public void remove(Card card) {
        expedited.remove(card);
    }
}
