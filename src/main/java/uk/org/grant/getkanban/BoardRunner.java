package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Arrays;
import java.util.Random;

public class BoardRunner {
    public static void main(String[] args) {
        int[] profits = new int[10000];
        for (int j = 0; j < 10000; j++) {
            Board b = new Board();
            b.addDice(new ActivityDice(Activity.ANALYSIS, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.ANALYSIS, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.DEVELOPMENT, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.DEVELOPMENT, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.DEVELOPMENT, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.TEST, new RandomDice(new Random())));
            b.addDice(new ActivityDice(Activity.TEST, new RandomDice(new Random())));

            b.setColumn(Column.Type.BACKLOG, new BacklogColumn());
            b.setColumn(Column.Type.SELECTED, new SelectedColumn(3, b.getColumn(Column.Type.BACKLOG)));
            b.setColumn(Column.Type.ANALYSIS, new ActivityColumn(Activity.ANALYSIS, 2, b.getColumn(Column.Type.SELECTED)));
            b.setColumn(Column.Type.DEVELOPMENT, new ActivityColumn(Activity.DEVELOPMENT, 4, b.getColumn(Column.Type.ANALYSIS)));
            b.setColumn(Column.Type.TEST, new ActivityColumn(Activity.TEST, 3, b.getColumn(Column.Type.DEVELOPMENT)));
            b.setColumn(Column.Type.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(Column.Type.TEST)));
            b.setColumn(Column.Type.DEPLOY, new DeployedColumn(b.getColumn(Column.Type.READY_TO_DEPLOY)));

            b.getColumn(Column.Type.READY_TO_DEPLOY).addCard(CardFactory.getCard("S1"));
            b.getColumn(Column.Type.TEST).addCard(CardFactory.getCard("S2"));
            b.getColumn(Column.Type.DEVELOPMENT).addCard(CardFactory.getCard("S3"));
            b.getColumn(Column.Type.DEVELOPMENT).addCard(CardFactory.getCard("S4"));
            b.getColumn(Column.Type.DEVELOPMENT).addCard(CardFactory.getCard("S5"));
            b.getColumn(Column.Type.DEVELOPMENT).addCard(CardFactory.getCard("S6"));
            b.getColumn(Column.Type.ANALYSIS).addCard(CardFactory.getCard("S7"));
            b.getColumn(Column.Type.ANALYSIS).addCard(CardFactory.getCard("S8"));
            b.getColumn(Column.Type.SELECTED).addCard(CardFactory.getCard("S9"));
            b.getColumn(Column.Type.SELECTED).addCard(CardFactory.getCard("S10"));
            b.getColumn(Column.Type.BACKLOG).addCard(CardFactory.getCard("S11"));
            b.getColumn(Column.Type.BACKLOG).addCard(CardFactory.getCard("S12"));
            b.getColumn(Column.Type.BACKLOG).addCard(CardFactory.getCard("S13"));
            b.getColumn(Column.Type.BACKLOG).addCard(CardFactory.getCard("S14"));

            for (int i = 9; i < 22; i++) {
                Day d = new Day(i);

                d.standUp(b);
                d.visit(b);
            }

            FinancialSummary summary = new FinancialSummary(b.getColumn(Column.Type.DEPLOY));
            profits[j] = summary.getTotalGrossProfitToDate(21);
        }
        Arrays.sort(profits);
        System.out.println("50%: " + profits[5000]);
        System.out.println("70%: " + profits[3000]);
        System.out.println("85%: " + profits[1500]);
        System.out.println("95%: " + profits[500]);
    }
}
