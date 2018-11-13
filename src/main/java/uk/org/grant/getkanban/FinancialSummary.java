package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.column.Column;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class FinancialSummary implements Comparable<FinancialSummary> {
    private AtomicIntegerArray newSubscribers = new AtomicIntegerArray(22);
    private boolean f1 = true;
    private boolean e1 = false;
    private final Column deployed;

    public FinancialSummary(Column deployed) {
        this.deployed = deployed;
        init();
    }

    private void init() {
        for (Card card : deployed.getCards()) {
            if (card.getName().equals("F1")) {
                if (card.getDayDeployed() <= card.getDueDate()) {
                    this.f1 = false;
                }
            }
            if (card.getName().equals("E1")) {
                if (card.getDayDeployed() <= 18) {
                    this.e1 = true;
                }
            }
            if (card.getDueDate() != -1) {
                newSubscribers.getAndAdd(card.getDueDate(), card.getSubscribers());
            }
            else if (card.getDayDeployed() <= 9) {
                newSubscribers.getAndAdd(9, card.getSubscribers());
            } else if (card.getDayDeployed() <= 12) {
                newSubscribers.getAndAdd(12, card.getSubscribers());
            } else if (card.getDayDeployed() <= 15) {
                newSubscribers.getAndAdd(15, card.getSubscribers());
            } else if (card.getDayDeployed() <= 18) {
                newSubscribers.getAndAdd(18, card.getSubscribers());
            } else {
                newSubscribers.getAndAdd(21, card.getSubscribers());
            }
        }
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

    public int getBillingCycleRevenue(int billingCycle) {
        int multiplier = 10 + ((billingCycle - 9) / 3) * 5;
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
        if (billingCycle == 15 && this.f1) {
            return -1500;
        } else if (billingCycle == 18 && this.e1) {
            return 4000;
        } else {
            return 0;
        }
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
