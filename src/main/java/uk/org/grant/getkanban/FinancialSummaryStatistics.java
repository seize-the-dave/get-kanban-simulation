package uk.org.grant.getkanban;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinancialSummaryStatistics {
    private List<FinancialSummary> summaries = new ArrayList<>();

    public void add(FinancialSummary summary) {
        summaries.add(summary);
        Collections.sort(summaries);
    }

    public FinancialSummary percentile(int percentile) {
        int inverse = 100 - percentile;
        return summaries.get((int) inverse * summaries.size() / 100);
    }
}
