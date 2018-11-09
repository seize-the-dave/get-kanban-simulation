package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.*;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DayTest {
    @Test
    public void daysCompleteCardsDuringWork() {
        Card card = Cards.getCard("S1");
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(card);

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        Board b = new Board();
        b.addDice(dice);

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
        assertThat(b.getStateColumn(State.ANALYSIS).getLimit(), is(2));

        Day d = new Day(1, board -> board.getStateColumn(State.ANALYSIS).setLimit(1));
        d.endOfDay(b);
        assertThat(b.getStateColumn(State.ANALYSIS).getLimit(), is(1));
    }
}
