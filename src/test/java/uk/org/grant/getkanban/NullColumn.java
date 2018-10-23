package uk.org.grant.getkanban;

import java.util.Optional;

public class NullColumn implements Column {
    @Override
    public Optional<Card> pullCard() {
        return Optional.empty();
    }

    @Override
    public void pullFromUpstream() {

    }
}
