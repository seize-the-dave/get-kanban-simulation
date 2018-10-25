package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.*;
import java.util.concurrent.*;

public class BoardRunner {
    private static int RUNS = 10000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                Board b = new Board();
                b.addDice(new ActivityDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.TEST, new RandomDice(new Random())));
                b.addDice(new ActivityDice(State.TEST, new RandomDice(new Random())));

                b.setColumn(State.BACKLOG, new BacklogColumn());
                b.setColumn(State.SELECTED, new SelectedColumn(3, b.getColumn(State.BACKLOG)));
                b.setColumn(State.ANALYSIS, new ActivityColumn(State.ANALYSIS, 2, b.getColumn(State.SELECTED)));
                b.setColumn(State.DEVELOPMENT, new ActivityColumn(State.DEVELOPMENT, 4, b.getColumn(State.ANALYSIS)));
                b.setColumn(State.TEST, new ActivityColumn(State.TEST, 3, b.getColumn(State.DEVELOPMENT)));
                b.setColumn(State.READY_TO_DEPLOY, new ReadyToDeployColumn(b.getColumn(State.TEST)));
                b.setColumn(State.DEPLOY, new DeployedColumn(b.getColumn(State.READY_TO_DEPLOY)));

                b.getColumn(State.READY_TO_DEPLOY).addCard(Cards.getCard("S1"));
                b.getColumn(State.TEST).addCard(Cards.getCard("S2"));
                b.getColumn(State.DEVELOPMENT).addCard(Cards.getCard("S3"));
                b.getColumn(State.DEVELOPMENT).addCard(Cards.getCard("S4"));
                b.getColumn(State.DEVELOPMENT).addCard(Cards.getCard("S5"));
                b.getColumn(State.DEVELOPMENT).addCard(Cards.getCard("S6"));
                b.getColumn(State.ANALYSIS).addCard(Cards.getCard("S7"));
                b.getColumn(State.ANALYSIS).addCard(Cards.getCard("S8"));
                b.getColumn(State.SELECTED).addCard(Cards.getCard("S9"));
                b.getColumn(State.SELECTED).addCard(Cards.getCard("S10"));
                b.getColumn(State.BACKLOG).addCard(Cards.getCard("S11"));
                b.getColumn(State.BACKLOG).addCard(Cards.getCard("S12"));
                b.getColumn(State.BACKLOG).addCard(Cards.getCard("S13"));
                b.getColumn(State.BACKLOG).addCard(Cards.getCard("S14"));

                for (int i = 9; i < 22; i++) {
                    Day d = Days.getDay(i);

                    d.standUp(b);
                    d.visit(b);
                    d.endOfDay(b);
                }

                FinancialSummary summary = new FinancialSummary(b.getColumn(State.DEPLOY));
                return summary.getTotalGrossProfitToDate(21);
            }
        };
        List<Callable<Integer>> runs = new ArrayList<>();
        for (int j = 0; j < RUNS; j++) {
            runs.add(callable);
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> results = service.invokeAll(runs);

        List<Integer> profitsList = new ArrayList<>();
        for (Future<Integer> result : results) {
            profitsList.add(result.get());
        }

        Collections.sort(profitsList);

        System.out.println("50%: " + profitsList.get(RUNS / 2));
        System.out.println("70%: " + profitsList.get(RUNS * 3 / 10));
        System.out.println("85%: " + profitsList.get(RUNS * 3 / 20));
        System.out.println("95%: " + profitsList.get(RUNS / 20));

        service.shutdown();
    }
}
