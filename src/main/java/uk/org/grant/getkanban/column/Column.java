package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.Optional;
import java.util.Queue;

public interface Column extends Workable<Context> {
    void addCard(StandardCard card);
    Queue<StandardCard> getCards();
    Optional<StandardCard> pull(Context context);
}
