package uk.org.grant.getkanban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;
import uk.org.grant.getkanban.policies.prioritisation.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.prioritisation.IntangiblesFirstPrioritisationStrategy;
import uk.org.grant.getkanban.policies.dice.NoCrossSkillingDiceAssignmentStrategy;
import uk.org.grant.getkanban.policies.prioritisation.WipAgingPrioritisationStrategy;

import java.util.*;
import java.util.concurrent.*;

public class BoardRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardRunner.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int runs = 10000;
        if (args.length == 1) {
            runs = Integer.parseInt(args[0]);
        }
        LOGGER.info("Running {} simulation(s) using {} thread(s)", runs, Runtime.getRuntime().availableProcessors());
        List<Callable<FinancialSummary>> summaries = new ArrayList<>();
        for (int j = 0; j < runs; j++) {
            summaries.add(() -> {
                Board b = new Board();
                b.addDice(new StateDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new StateDice(State.ANALYSIS, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.DEVELOPMENT, new RandomDice(new Random())));
                b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));
                b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));

                b.getOptions().orderBy(
                        new IntangiblesFirstPrioritisationStrategy()
                        .thenComparing(new BusinessValuePrioritisationStrategy()));
                b.getSelected().orderBy(
                        new WipAgingPrioritisationStrategy());
                b.getStateColumn(State.ANALYSIS).orderBy(
                        new WipAgingPrioritisationStrategy());
                b.getStateColumn(State.DEVELOPMENT).orderBy(
                        new WipAgingPrioritisationStrategy());
                b.getStateColumn(State.TEST).orderBy(
                        new WipAgingPrioritisationStrategy());
                b.getReadyToDeploy().orderBy(
                        new WipAgingPrioritisationStrategy());
                b.getDeployed().orderBy(
                        new WipAgingPrioritisationStrategy());

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

                b.getOptions().addCard(Cards.getCard("S11"));
                b.getOptions().addCard(Cards.getCard("S12"));
                b.getOptions().addCard(Cards.getCard("S13"));
                b.getOptions().addCard(Cards.getCard("S14"));
                b.getOptions().addCard(Cards.getCard("S15"));
                b.getOptions().addCard(Cards.getCard("S16"));
                b.getOptions().addCard(Cards.getCard("S17"));
                b.getOptions().addCard(Cards.getCard("S18"));

                b.getOptions().addCard(Cards.getCard("F1"));
                b.getOptions().addCard(Cards.getCard("F2"));

                b.getOptions().addCard(Cards.getCard("I1"));
                b.getOptions().addCard(Cards.getCard("I2"));
                b.getOptions().addCard(Cards.getCard("I3"));

                DaysFactory daysFactory = new DaysFactory(true, new NoCrossSkillingDiceAssignmentStrategy());
                for (int i = 10; i < 22; i++) {
                    Day d = daysFactory.getDay(i);

                    d.standUp(b);
                    d.doTheWork(new Context(b, d));
                    d.endOfDay(b);
                }

                return new FinancialSummary(b);
            });
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<FinancialSummary>> results = service.invokeAll(summaries);

        List<FinancialSummary> profitsList = new ArrayList<>();
        for (Future<FinancialSummary> result : results) {
            profitsList.add(result.get());
        }
        service.shutdown();

        Collections.sort(profitsList);


        percentile(50, profitsList);
        percentile(70, profitsList);
        percentile(85, profitsList);
        percentile(95, profitsList);
    }

    private static void percentile(int percentage, List<FinancialSummary> profitsList) {
        int inverse = 100 - percentage;
        System.out.println("\n" + percentage + "%:\n\n" + profitsList.get((profitsList.size() / 100) * inverse));
    }
}
