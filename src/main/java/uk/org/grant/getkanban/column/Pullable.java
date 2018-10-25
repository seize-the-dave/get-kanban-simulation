package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.card.Card;

import java.util.Optional;

public interface Pullable {
    Optional<Card> pull();
}
