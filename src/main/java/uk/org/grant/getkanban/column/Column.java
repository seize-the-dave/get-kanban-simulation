package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface Column extends Workable<Context> {
    void addCard(Card card, ClassOfService cos);
    List<Card> getCards();
    Optional<Card> pull(Context context, ClassOfService cos);
    void orderBy(Comparator<Card> comparator);
    void clear();
}
