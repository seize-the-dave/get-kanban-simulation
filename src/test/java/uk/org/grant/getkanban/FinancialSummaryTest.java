package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.column.DeployedColumn;
import uk.org.grant.getkanban.column.NullColumn;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FinancialSummaryTest {
    @Test
    public void testNewSubscribersThisBillingCycle() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getNewSubscribers(9), is(20));
        assertThat(summary.getNewSubscribers(12), is(0));
        assertThat(summary.getNewSubscribers(15), is(0));
        assertThat(summary.getNewSubscribers(18), is(0));
        assertThat(summary.getNewSubscribers(21), is(0));
    }

    private Board makeBoard() {
        return new Board();
    }

    @Test
    public void testTotalSubscribersToDate() {
        FinancialSummary summary = new FinancialSummary(makeBoard());
        assertThat(summary.getTotalSubscribersToDate(9), is(20));
        assertThat(summary.getTotalSubscribersToDate(12), is(20));
        assertThat(summary.getTotalSubscribersToDate(15), is(20));
        assertThat(summary.getTotalSubscribersToDate(18), is(20));
        assertThat(summary.getTotalSubscribersToDate(21), is(20));
    }

    @Test
    public void testFinesAndPayments() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getFinesOrPayments(15), is(-1500));
        assertThat(summary.getFinesOrPayments(18), is(0));
    }

    @Test
    public void testNoFineForF1WhenDelivered() {
        Card f1 = Cards.getCard("F1");
        f1.onSelected(new Context(new Board(), new DaysFactory(true).getDay(10)));
        f1.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(15)));

        Board b = new Board();
        DeployedColumn deployed = b.getDeployed();
        deployed.addCard(f1, ClassOfService.STANDARD);

        FinancialSummary summary = new FinancialSummary(b);
        assertThat(summary.getFinesOrPayments(15), is(-1500));
    }

    @Test
    public void testFineForF1WhenLate() {
        Board b = new Board();

        FinancialSummary summary = new FinancialSummary(b);
        assertThat(summary.getFinesOrPayments(15), is(-1500));
    }

    @Test
    public void testNoPaymentForE1WhenLate() {
        Card e1 = Cards.getCard("E1");
        e1.onSelected(new Context(new Board(), new DaysFactory(true).getDay(15)));
        e1.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(19)));

        Board b = new Board();
        DeployedColumn deployed = b.getDeployed();
        deployed.addCard(e1, ClassOfService.STANDARD);

        FinancialSummary summary = new FinancialSummary(b);
        assertThat(summary.getFinesOrPayments(18), is(0));
    }

    @Test
    public void testBillingCycleRevenue() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getBillingCycleRevenue(9), is(200));
        assertThat(summary.getBillingCycleRevenue(12), is(300));
        assertThat(summary.getBillingCycleRevenue(15), is(400));
        assertThat(summary.getBillingCycleRevenue(18), is(500));
        assertThat(summary.getBillingCycleRevenue(21), is(600));
    }

    @Test
    public void testBillingCycleGrossProfit() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getBillingCycleGrossProfit(9), is(200));
        assertThat(summary.getBillingCycleGrossProfit(12), is(300));
        assertThat(summary.getBillingCycleGrossProfit(15), is(-1100));
        assertThat(summary.getBillingCycleGrossProfit(18), is(500));
        assertThat(summary.getBillingCycleGrossProfit(21), is(600));
    }

    @Test
    public void testGrossProfitToDate() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getTotalGrossProfitToDate(9), is(200));
        assertThat(summary.getTotalGrossProfitToDate(12), is(500));
        assertThat(summary.getTotalGrossProfitToDate(15), is(-600));
        assertThat(summary.getTotalGrossProfitToDate(18), is(-100));
        assertThat(summary.getTotalGrossProfitToDate(21), is(500));
    }
}
