package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;

import java.util.*;

public class DeployedColumn extends UnbufferedColumn {

    public DeployedColumn(Column upstream) {
        super(upstream);
    }

    @Override
    public void visit(Day day) {
        while (true) {
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                optionalCard.get().setDayDeployed(day.getOrdinal());
                addCard(optionalCard.get());
            } else {
                break;
            }
        }
    }
}
