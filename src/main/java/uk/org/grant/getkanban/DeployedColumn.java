package uk.org.grant.getkanban;

import java.util.*;

public class DeployedColumn extends UnbufferedColumn {

    public DeployedColumn(Column upstream) {
        super(upstream);
    }

    @Override
    public void visit(Day day) {
        Optional<Card> optionalCard = upstream.pull();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setDayDeployed(day.getOrdinal());
            addCard(card);
        }
    }
}
