package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.FixedDateCard;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class FinancialSummary implements Comparable<FinancialSummary> {
    private AtomicIntegerArray newSubscribers = new AtomicIntegerArray(22);
    private AtomicIntegerArray finesAndPayments = new AtomicIntegerArray(22);
    private final Board board;

    public FinancialSummary(Board board) {
        this.board = board;
        init();
    }

    private void init() {
        for (Card card : board.getCards()) {
            if (card instanceof FixedDateCard) {
                newSubscribers.getAndAdd(card.getDueDate(), card.getSubscribers());
                finesAndPayments.getAndAdd(card.getDueDate(), card.getFineOrPayment());
            } else if (card.getDayDeployed() > 0) {
                newSubscribers.getAndAdd(getBillingDay(card.getDayDeployed()), card.getSubscribers());
            }
        }
    }

    public static int getBillingDay(int dayDeployed) {
        return (dayDeployed + 2) / 3 * 3;
    }

    public int getNewSubscribers(int billingCycle) {
        return newSubscribers.get(billingCycle);
    }

    public int getTotalSubscribersToDate(int billingCycle) {
        if (billingCycle == 9) {
            return newSubscribers.get(billingCycle);
        } else {
            return newSubscribers.get(billingCycle) + getTotalSubscribersToDate(billingCycle - 3);
        }
    }

    public static int getRevenueMultiplier(int billingCycle) {
        return 10 + ((billingCycle - 9) / 3) * 5;
    }

    public int getBillingCycleRevenue(int billingCycle) {
        int multiplier = getRevenueMultiplier(billingCycle);
        return getTotalSubscribersToDate(billingCycle) * multiplier;
    }

    public int getBillingCycleGrossProfit(int billingCycle) {
        return getBillingCycleRevenue(billingCycle) + getFinesOrPayments(billingCycle);
    }

    public int getTotalGrossProfitToDate(int billingCycle) {
        if (billingCycle == 9) {
            return getBillingCycleGrossProfit(billingCycle);
        } else {
            return getTotalGrossProfitToDate(billingCycle - 3) + getBillingCycleGrossProfit(billingCycle);
        }
    }

    public int getFinesOrPayments(int billingCycle) {
        return finesAndPayments.get(billingCycle);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%1$-20s", ""));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft("Day " + day));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "New Subscribers"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getNewSubscribers(day)));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "Total Subscribers"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getTotalSubscribersToDate(day)));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "Cycle Revenue"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getBillingCycleRevenue(day)));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "Fines or Payments"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getFinesOrPayments(day)));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "Cycle Gross Profit"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getBillingCycleGrossProfit(day)));
        }
        sb.append("\n");
        sb.append(String.format("%1$-20s", "Gross Profit To Date"));
        for (int i = 0; i < 5; i++) {
            int day = 9 + (i * 3);
            sb.append(padLeft(getTotalGrossProfitToDate(day)));
        }

        return sb.toString();
    }

    public String padLeft(String s) {
        return String.format("%1$8s", s);
    }

    public String padLeft(int i) {
        return String.format("%1$8s", i);
    }

    @Override
    public int compareTo(FinancialSummary o) {
        return getTotalGrossProfitToDate(21) - o.getTotalGrossProfitToDate(21);
    }
}
