package uk.org.grant.getkanban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.policies.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

public class BoardRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardRunner.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int runs = 10000;
        if (args.length == 1) {
            runs = Integer.parseInt(args[0]);
        }

        // Configuration

        // Prioritisation
        List<RunConfiguration> configurations = new ArrayList<>();
//        configurations.add(
//                new RunConfiguration(
//                        new ExpeditesPrioritisationStrategy()
//                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
//                                .thenComparing(c -> c.getName()),
//                        new ExpeditesPrioritisationStrategy()
//                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
//                                .thenComparing(c -> c.getName()),
//                        new ComplexDiceAssignmentStrategy(BigDecimal.ONE, 2),
//                        Boolean.TRUE,
//                        new WipLimitAdjustment(10,0, 3, 2, 4, 3)));
//        configurations.add(
//                new RunConfiguration(
//                        new ExpeditesPrioritisationStrategy()
//                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
//                                .thenComparing(c -> c.getName()),
//                        new ExpeditesPrioritisationStrategy()
//                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
//                                .thenComparing(c -> c.getName()),
//                        new ComplexDiceAssignmentStrategy(BigDecimal.ONE, 2),
//                        Boolean.TRUE,
//                        new WipLimitAdjustment(10,0, 2, 2, 4, 3)));
        // 15,800 (Best So Far)
        configurations.add(
                new RunConfiguration(
                        new ExpeditesPrioritisationStrategy()
                                .thenComparing(new IntangiblesFirstPrioritisationStrategy())
                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
                                .thenComparing(c -> c.getName()),
                        new ExpeditesPrioritisationStrategy()
                                .thenComparing(new IntangiblesFirstPrioritisationStrategy())
                                .thenComparing(new WeightedShortestJobFirstPrioritisationStrategy())
                                .thenComparing(c -> c.getName()),
                        new ComplexDiceAssignmentStrategy(new BigDecimal(0.5), 2),
                        Boolean.FALSE,
                        new WipLimitAdjustment(11,0, 2, 2, 4, 3)));
        // Naive
        // configurations.add(
        //         new RunConfiguration(
        //                 new ExpeditesPrioritisationStrategy()
        //                         .thenComparing(new FixedDateCardsPrioritisationStrategy())
        //                         .thenComparing(new BusinessValuePrioritisationStrategy())
        //                         .thenComparing(c -> c.getName()),
        //                 new ExpeditesPrioritisationStrategy()
        //                         .thenComparing(new FixedDateCardsPrioritisationStrategy())
        //                         .thenComparing(new BusinessValuePrioritisationStrategy())
        //                         .thenComparing(c -> c.getName()),
        //                 new ComplexDiceAssignmentStrategy(BigDecimal.ONE, 3),
        //                 Boolean.TRUE));

        for (RunConfiguration config : configurations) {
            BoardRunner champRunner = new BoardRunner(config);
            champRunner.run(runs);
        }
    }

    private RunConfiguration config;

    public BoardRunner(RunConfiguration config) {
        this.config = config;
    }

    public void run(int runs) throws InterruptedException, ExecutionException {
        // Runner
        LOGGER.info("Running {} simulation(s) using {} thread(s)", runs, Runtime.getRuntime().availableProcessors());
        List<Callable<FinancialSummary>> summaries = new ArrayList<>();
        for (int j = 0; j < runs; j++) {
            summaries.add(() -> {
                Board b = new Board();
                DaysFactory daysFactory = new DaysFactory(config.supportsTraining(), config.getDiceAssignmentStrategy());
                DayStore.setDay(daysFactory.getDay(9));


                b.getOptions().orderBy(config.getBacklogComparator());
                b.getSelected().orderBy(config.getActivityComparator());
                b.getStateColumn(State.ANALYSIS).orderBy(config.getActivityComparator());
                b.getStateColumn(State.DEVELOPMENT).orderBy(config.getActivityComparator());
                b.getStateColumn(State.TEST).orderBy(config.getActivityComparator());
                b.getReadyToDeploy().orderBy(Comparator.comparing(c -> c.getName()));
                b.getDeployed().orderBy(Comparator.comparing(c -> c.getName()));

                for (WipLimitAdjustment adjustment : config.getWipLimitAdjustments()) {
                    b.putAdjustment(adjustment);
                }

                for (int i = 10; i < 22; i++) {
                    LOGGER.info(b.toString());
                    Day d = daysFactory.getDay(i);
                    DayStore.setDay(d);

                    d.standUp(b);
                    d.doTheWork(new Context(b, d));
                    d.endOfDay(b);
                }

                return new FinancialSummary(b);
            });
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<FinancialSummary>> results = service.invokeAll(summaries);

        FinancialSummaryStatistics stats = new FinancialSummaryStatistics();
        for (Future<FinancialSummary> result : results) {
            stats.add(result.get());
        }
        service.shutdown();

        percentile(25, stats);
        percentile(50, stats);
        percentile(75, stats);
        percentile(100, stats);
    }

    private static void percentile(int percentage, FinancialSummaryStatistics stats) {
        System.out.println("P" + percentage + "\n" + stats.percentile(percentage) + "\n");
    }
}
