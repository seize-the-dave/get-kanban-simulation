package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DayTest {
    @Test
    public void daysAllocateDiceDuringStandup() {
        ActivityDice dice = new ActivityDice(State.ANALYSIS, new LoadedDice(6));

        Board b = new Board();
        b.addDice(dice);
        b.setColumn(State.BACKLOG, new BacklogColumn());
        b.setColumn(State.SELECTED, new SelectedColumn(3, b.getColumn(State.BACKLOG)));
        b.setColumn(State.ANALYSIS, new ActivityColumn(State.ANALYSIS, 2, b.getColumn(State.SELECTED)));
        b.setColumn(State.DEVELOPMENT, new ActivityColumn(State.DEVELOPMENT, 4, b.getColumn(State.ANALYSIS)));
        b.setColumn(State.TEST, new ActivityColumn(State.TEST, 3, b.getColumn(State.DEVELOPMENT)));
        b.setColumn(State.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(State.TEST)));
        b.setColumn(State.DEPLOY, new DeployedColumn(b.getColumn(State.READY_TO_DEPLOY)));

        Day day = Days.getDay(1);
        day.standUp(b);

        assertThat(b.getColumn(State.ANALYSIS).getAllocatedDice(), hasItem(dice));
    }

    @Test
    public void daysCompleteCardsDuringWork() {
        Card card = Cards.getCard("S1");
        ActivityColumn analysis = new ActivityColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(card);
        ActivityDice dice = new ActivityDice(State.ANALYSIS, new LoadedDice(6));
        Board b = new Board();
        b.addDice(dice);
        b.setColumn(State.BACKLOG, new BacklogColumn());
        b.setColumn(State.SELECTED, new SelectedColumn(3, b.getColumn(State.BACKLOG)));
        b.setColumn(State.ANALYSIS, new ActivityColumn(State.ANALYSIS, 2, b.getColumn(State.SELECTED)));
        b.setColumn(State.DEVELOPMENT, new ActivityColumn(State.DEVELOPMENT, 4, b.getColumn(State.ANALYSIS)));
        b.setColumn(State.TEST, new ActivityColumn(State.TEST, 3, b.getColumn(State.DEVELOPMENT)));
        b.setColumn(State.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(State.TEST)));
        b.setColumn(State.DEPLOY, new DeployedColumn(b.getColumn(State.READY_TO_DEPLOY)));

        Day day = Days.getDay(1);
        day.standUp(b);
        day.visit(b);

        assertThat(card.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void canGetOrdinal() {
        Day day = Days.getDay(1);
        assertThat(day.getOrdinal(), is(1));
    }

    @Test
    public void executesInstructionsAtEndOfDay() {
        Board b = new Board();
        b.setColumn(State.ANALYSIS, new ActivityColumn(State.ANALYSIS, new NullColumn()));
        assertThat(((Limited) b.getColumn(State.ANALYSIS)).getLimit(), is(Integer.MAX_VALUE));

        Day d = new Day(1, board -> ((Limited) board.getColumn(State.ANALYSIS)).setLimit(1));
        d.endOfDay(b);
        assertThat(((Limited) b.getColumn(State.ANALYSIS)).getLimit(), is(1));
    }
}
