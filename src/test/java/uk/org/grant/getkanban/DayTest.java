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
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new LoadedDice(6));

        Board b = new Board();
        b.addDice(dice);
        b.setColumn(Column.Type.BACKLOG, new BacklogColumn());
        b.setColumn(Column.Type.SELECTED, new SelectedColumn(3, b.getColumn(Column.Type.BACKLOG)));
        b.setColumn(Column.Type.ANALYSIS, new ActivityColumn(Activity.ANALYSIS, 2, b.getColumn(Column.Type.SELECTED)));
        b.setColumn(Column.Type.DEVELOPMENT, new ActivityColumn(Activity.DEVELOPMENT, 4, b.getColumn(Column.Type.ANALYSIS)));
        b.setColumn(Column.Type.TEST, new ActivityColumn(Activity.TEST, 3, b.getColumn(Column.Type.DEVELOPMENT)));
        b.setColumn(Column.Type.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(Column.Type.TEST)));
        b.setColumn(Column.Type.DEPLOY, new DeployedColumn(b.getColumn(Column.Type.READY_TO_DEPLOY)));

        Day day = Days.getDay(1);
        day.standUp(b);

        assertThat(b.getColumn(Column.Type.ANALYSIS).getAllocatedDice(), hasItem(dice));
    }

    @Test
    public void daysCompleteCardsDuringWork() {
        Card card = Cards.getCard("S1");
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        analysis.addCard(card);
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new LoadedDice(6));
        Board b = new Board();
        b.addDice(dice);
        b.setColumn(Column.Type.BACKLOG, new BacklogColumn());
        b.setColumn(Column.Type.SELECTED, new SelectedColumn(3, b.getColumn(Column.Type.BACKLOG)));
        b.setColumn(Column.Type.ANALYSIS, new ActivityColumn(Activity.ANALYSIS, 2, b.getColumn(Column.Type.SELECTED)));
        b.setColumn(Column.Type.DEVELOPMENT, new ActivityColumn(Activity.DEVELOPMENT, 4, b.getColumn(Column.Type.ANALYSIS)));
        b.setColumn(Column.Type.TEST, new ActivityColumn(Activity.TEST, 3, b.getColumn(Column.Type.DEVELOPMENT)));
        b.setColumn(Column.Type.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(Column.Type.TEST)));
        b.setColumn(Column.Type.DEPLOY, new DeployedColumn(b.getColumn(Column.Type.READY_TO_DEPLOY)));

        Day day = Days.getDay(1);
        day.standUp(b);
        day.visit(b);

        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(0));
    }

    @Test
    public void canGetOrdinal() {
        Day day = Days.getDay(1);
        assertThat(day.getOrdinal(), is(1));
    }

    @Test
    public void executesInstructionsAtEndOfDay() {
        Board b = new Board();
        b.setColumn(Column.Type.ANALYSIS, new ActivityColumn(Activity.ANALYSIS, new NullColumn()));
        assertThat(((Limited) b.getColumn(Column.Type.ANALYSIS)).getLimit(), is(Integer.MAX_VALUE));

        Day d = new Day(1, board -> ((Limited) board.getColumn(Column.Type.ANALYSIS)).setLimit(1));
        d.endOfDay(b);
        assertThat(((Limited) b.getColumn(Column.Type.ANALYSIS)).getLimit(), is(1));
    }
}
