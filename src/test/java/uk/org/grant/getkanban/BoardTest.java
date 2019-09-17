package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void testGetDiceFromBoard() {
        StateDice dice = new StateDice(State.ANALYSIS, new RandomDice());
        Board board = new Board();
        board.addDice(dice);
        assertThat(board.getDice(), hasItems(dice));
    }

    @Test
    public void canRemoveDiceBoard() {
        StateDice dice = new StateDice(State.ANALYSIS, new RandomDice());
        Board board = new Board();
        board.addDice(dice);

        assertThat(board.getDice(), hasItems(dice));

        List<StateDice> stateDie = new LinkedList<>(board.getDice());
        for (StateDice stateDice : stateDie) {
            board.removeDice(stateDice);
        }
        assertThat(board.getDice(), empty());
    }

    @Test
    public void getCardsReturnsCardsFromColumns() {
        Board b = new Board();
        assertThat(b.getCards(), is(not(empty())));
        Card e1 = Cards.getCard("E1");

        b.getOptions().addCard(e1, ClassOfService.STANDARD);

        assertThat(e1, isIn(b.getCards()));
    }
}
