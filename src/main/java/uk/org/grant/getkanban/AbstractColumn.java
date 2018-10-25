package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.Collections;
import java.util.List;

public abstract class AbstractColumn implements Column {
    @Override
    public int getLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setLimit(int limit) {
        // Do nothing
    }

    @Override
    public void allocateDice(StateDice... dice) {
        // Do nothing
    }

    @Override
    public List<StateDice> getAllocatedDice() {
        return Collections.emptyList();
    }
}
