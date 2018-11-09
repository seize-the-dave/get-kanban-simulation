package uk.org.grant.getkanban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DiceAssignmentStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiceAssignmentStrategy.class);

    public void assignDice(Board board) {
        for (State state : new State[] {State.TEST, State.DEVELOPMENT, State.ANALYSIS}) {
            StateColumn column = board.getStateColumn(state);
            List<StateDice> dice = board.getDice(state);
            LOGGER.info("Ready to allocate {} to {}", dice, column);
            List<Card> cards = column.getCards().stream().filter(c -> c.getRemainingWork(state) != 0).collect(Collectors.toList());
            if (cards.size() == 0) {
                LOGGER.debug("Can't allocate for {}", state);
            }
            List<DiceGroup> groups = new ArrayList<DiceGroup>();
            for (Card card : cards) {
                int allocation = new BigDecimal(card.getRemainingWork(state)).divide(new BigDecimal(3.5), RoundingMode.UP).intValue();
                if (allocation >= dice.size()) {
                    groups.add(new DiceGroup(card, dice.toArray(new StateDice[0])));

                    break;
                } else {
                    List<StateDice> allocatedDice = dice.subList(0, allocation);
                    groups.add(new DiceGroup(card, allocatedDice.toArray(new StateDice[0])));
                    dice.removeAll(allocatedDice);
                }
            }
            column.assignDice(groups.toArray(new DiceGroup[0]));
        }
    }
}
