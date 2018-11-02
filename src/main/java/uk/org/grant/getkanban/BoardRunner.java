package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.*;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.*;
import java.util.concurrent.*;

public class BoardRunner {
    private static int RUNS = 1;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<FinancialSummary> callable = new Callable<FinancialSummary>() {
            @Override
            public FinancialSummary call() {
                Board b = new Board();
                b.addDice(new StateDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new StateDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));
                b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));

                b.getDeployed().addCard(Cards.getCard("S1"));
                b.getDeployed().addCard(Cards.getCard("S2"));
                b.getDeployed().addCard(Cards.getCard("S4"));

                b.getStateColumn(State.TEST).addCard(Cards.getCard("S3"));

                b.getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S5"));
                b.getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S6"));
                b.getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S7"));
                b.getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S9"));

                b.getStateColumn(State.ANALYSIS).addCard(Cards.getCard("S8"));
                b.getStateColumn(State.ANALYSIS).addCard(Cards.getCard("S10"));

                b.getSelected().addCard(Cards.getCard("S13"));

                b.getBacklog().addCard(Cards.getCard("S11"));
                b.getBacklog().addCard(Cards.getCard("S12"));
                b.getBacklog().addCard(Cards.getCard("S13"));
                b.getBacklog().addCard(Cards.getCard("S14"));
                b.getBacklog().addCard(Cards.getCard("S15"));
                b.getBacklog().addCard(Cards.getCard("S16"));
                b.getBacklog().addCard(Cards.getCard("S17"));
                b.getBacklog().addCard(Cards.getCard("S18"));

                b.getBacklog().addCard(Cards.getCard("F1"));
                b.getBacklog().addCard(Cards.getCard("F2"));

                b.getBacklog().addCard(Cards.getCard("I1"));
                b.getBacklog().addCard(Cards.getCard("I2"));
                b.getBacklog().addCard(Cards.getCard("I3"));

                DaysFactory daysFactory = new DaysFactory(true);
                for (int i = 10; i < 22; i++) {
                    Day d = daysFactory.getDay(i);

                    d.standUp(b);
                    d.doTheWork(new Context(b, d));
                    d.endOfDay(b);
                }

                return new FinancialSummary(b.getDeployed());
            }
        };
        List<Callable<FinancialSummary>> runs = new ArrayList<>();
        for (int j = 0; j < RUNS; j++) {
            runs.add(callable);
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<FinancialSummary>> results = service.invokeAll(runs);

        List<FinancialSummary> profitsList = new ArrayList<>();
        for (Future<FinancialSummary> result : results) {
            profitsList.add(result.get());
        }
        service.shutdown();

        Collections.sort(profitsList);

        System.out.println("\n50th Percentile:\n\n" + profitsList.get(RUNS / 2));
        System.out.println("\n70th Percentile:\n\n" + profitsList.get(RUNS * 3 / 10));
        System.out.println("\n85th Percentile:\n\n" + profitsList.get(RUNS * 3 / 20));
        System.out.println("\n95th Percentile:\n\n" + profitsList.get(RUNS / 20));
    }
}
