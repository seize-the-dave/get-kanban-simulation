package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;
import java.util.Optional;
import java.util.Queue;

public interface Column extends Workable<Context> {
    void addCard(Card card);
    Queue<Card> getCards();
    Optional<Card> pull(Context context);
    void orderBy(Comparator<Card> comparator);
}
