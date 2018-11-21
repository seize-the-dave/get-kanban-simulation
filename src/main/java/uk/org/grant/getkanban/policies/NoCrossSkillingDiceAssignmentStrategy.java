package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;

import java.util.List;
import java.util.Optional;

public class NoCrossSkillingDiceAssignmentStrategy implements DiceAssignmentStrategy {
    @Override
    public void assignDice(Board board) {
        for (State state: State.values()) {
            StateColumn column = board.getStateColumn(state);
            List<StateDice> dice = board.getDice(state);

            Optional<Card> card = column.getIncompleteCards().stream().filter(c -> !c.isBlocked()).findFirst();
            if (card.isPresent()) {
                DiceGroup grp = new DiceGroup(card.get(), dice.toArray(new StateDice[dice.size()]));
                column.assignDice(grp);
            }
        }
    }
}
