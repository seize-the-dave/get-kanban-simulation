package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.*;

import java.util.concurrent.atomic.AtomicInteger;

public class CarlosHired implements Instruction {
    private final AtomicInteger store;

    public CarlosHired(AtomicInteger store) {
        this.store = store;
    }

    @Override
    public void execute(Board b) {
        Limited c = (Limited) b.getColumn(State.TEST);
        store.set(c.getLimit());
        c.setLimit(Integer.MAX_VALUE);
    }
}
