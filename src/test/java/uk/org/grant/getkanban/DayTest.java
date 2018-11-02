package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.*;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DayTest {
    @Test
    public void daysAllocateDiceDuringStandup() {
        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));

        Board b = new Board();
        b.addDice(dice);
        b.setColumn(State.BACKLOG, new BacklogColumn());
        b.setColumn(State.SELECTED, new SelectedColumn(3, b.getColumn(State.BACKLOG)));
        b.setColumn(State.ANALYSIS, new StateColumn(State.ANALYSIS, 2, b.getColumn(State.SELECTED)));
        b.setColumn(State.DEVELOPMENT, new StateColumn(State.DEVELOPMENT, 4, b.getColumn(State.ANALYSIS)));
        b.setColumn(State.TEST, new StateColumn(State.TEST, 3, b.getColumn(State.DEVELOPMENT)));
        b.setColumn(State.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(State.TEST)));
        b.setColumn(State.DEPLOY, new DeployedColumn(b.getColumn(State.READY_TO_DEPLOY)));

        DaysFactory daysFactory = new DaysFactory(true);
        Day day = daysFactory.getDay(1);
        day.standUp(b);

        assertThat(b.getColumn(State.ANALYSIS).getAssignedDice(), hasItem(dice));
    }

    @Test
    public void daysCompleteCardsDuringWork() {
        Card card = Cards.getCard("S1");
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(card);
        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        Board b = new Board();
        b.addDice(dice);
        b.setColumn(State.BACKLOG, new BacklogColumn());
        b.setColumn(State.SELECTED, new SelectedColumn(3, b.getColumn(State.BACKLOG)));
        b.setColumn(State.ANALYSIS, new StateColumn(State.ANALYSIS, 2, b.getColumn(State.SELECTED)));
        b.setColumn(State.DEVELOPMENT, new StateColumn(State.DEVELOPMENT, 4, b.getColumn(State.ANALYSIS)));
        b.setColumn(State.TEST, new StateColumn(State.TEST, 3, b.getColumn(State.DEVELOPMENT)));
        b.setColumn(State.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(State.TEST)));
        b.setColumn(State.DEPLOY, new DeployedColumn(b.getColumn(State.READY_TO_DEPLOY)));

        DaysFactory daysFactory = new DaysFactory(true);
        Day day = daysFactory.getDay(1);
        day.standUp(b);
        day.doTheWork(new Context(b, day));

        assertThat(card.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void canGetOrdinal() {
        DaysFactory daysFactory = new DaysFactory(true);
        Day day = daysFactory.getDay(1);
        assertThat(day.getOrdinal(), is(1));
    }

    @Test
    public void executesInstructionsAtEndOfDay() {
        Board b = new Board();
        b.setColumn(State.ANALYSIS, new StateColumn(State.ANALYSIS, new NullColumn()));
        assertThat(b.getColumn(State.ANALYSIS).getLimit(), is(Integer.MAX_VALUE));

        Day d = new Day(1, board -> board.getColumn(State.ANALYSIS).setLimit(1));
        d.endOfDay(b);
        assertThat(b.getColumn(State.ANALYSIS).getLimit(), is(1));
    }
}
