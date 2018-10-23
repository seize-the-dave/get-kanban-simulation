package uk.org.grant.getkanban;

import java.util.Optional;

public interface Pullable {
    Optional<Card> pull();
}
