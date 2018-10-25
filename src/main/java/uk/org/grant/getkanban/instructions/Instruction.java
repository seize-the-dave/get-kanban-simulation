package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;

public interface Instruction {
    void execute(Board b);
}
