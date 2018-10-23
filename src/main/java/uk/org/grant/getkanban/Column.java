package uk.org.grant.getkanban;

import java.util.Optional;

public interface Column {
    Optional<Card> pullCard();
    void pullFromUpstream();
}
