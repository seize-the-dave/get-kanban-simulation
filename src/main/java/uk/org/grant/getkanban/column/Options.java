package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.ExpediteCard;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.IntangiblesFirstPrioritisationStrategy;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Card> getCards() {
        return cards.stream().sorted(cards.getComparator()).collect(Collectors.toList());
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            Optional<Card> expeditable = cards.stream().filter(c -> c.isExpeditable(context.getDay())).filter(c -> c.getName() != "E2").findFirst();
            if (expeditable.isPresent()) {
                cards.remove(expeditable.get());
            }
            return expeditable;
        } else {
            Optional<Card> card = cards.stream().filter(c -> c instanceof ExpediteCard == false).findFirst();
            if (card.isPresent()) {
                cards.remove(card.get());
            }
            return card;
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
