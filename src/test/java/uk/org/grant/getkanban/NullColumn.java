package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NullColumn implements Column {
    @Override
    public Optional<Card> pullCard() {
        return Optional.empty();
    }

    @Override
    public void pullFromUpstream() {

    }

    @Override
    public void allocateDice(ActivityDice... dice) {

    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        return Collections.EMPTY_LIST;
    }
}
