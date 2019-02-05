package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.ExpediteCard;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.IntangiblesFirstPrioritisationStrategy;

import javax.swing.text.html.Option;
import java.util.*;

public class Options extends UnbufferedColumn {
    protected final MutablePriorityQueue<Card> cards;

    public Options() {
        this(new IntangiblesFirstPrioritisationStrategy().thenComparing(new BusinessValuePrioritisationStrategy()));
    }

    public Options(Comparator<Card> comparator) {
        super(new NullColumn(), comparator);
        this.cards = new MutablePriorityQueue<>(comparator);
    }

    @Override
    public void doTheWork(Context context) {
        // Nothing to do in the backlog
    }

    @Override
    public void addCard(Card card, ClassOfService cos) {
        cards.add(card);
    }

    @Override
    public Queue<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            Card topOfBacklog = cards.peek();
            if (topOfBacklog != null && topOfBacklog.isExpeditable(context.getDay())) {
                return Optional.ofNullable(cards.poll());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.ofNullable(cards.poll());
        }
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        cards.setComparator(comparator);
    }

    @Override
    public String toString() {
        return "[BACKLOG (" + cards.size() + "/∞)]";
    }
}
