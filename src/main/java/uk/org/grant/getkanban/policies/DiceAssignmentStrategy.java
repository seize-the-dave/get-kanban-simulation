package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.Board;

public interface DiceAssignmentStrategy {
    void assignDice(Board board);
}
