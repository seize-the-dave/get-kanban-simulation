package uk.org.grant.getkanban.instructions;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.Dice;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.dice.StateDice;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class TedsTrainingOpportunityTest {
    @Test
    public void shouldRemoveTesterDiceIfTedGoesTraining() {
        Instruction ted = new TedsTrainingOpportunity(true);
        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));

        ted.execute(b);

        assertThat(b.getDice(State.TEST), hasSize(2));
    }

    @Test
    public void shouldKeepTesterDiceIfTedStaysHome() {
        StateDice dice = new StateDice(State.TEST, new LoadedDice(6));
        Instruction ted = new TedsTrainingOpportunity(false);

        Board b = new Board();
        b.addDice(dice);
        ted.execute(b);

        assertThat(b.getDice(State.TEST), hasItem(dice));
    }
}
