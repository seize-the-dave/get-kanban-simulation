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

        assertThat(summary.getNewSubscribers(9), is(2));
        assertThat(summary.getNewSubscribers(12), is(3));
        assertThat(summary.getNewSubscribers(15), is(4));
        assertThat(summary.getNewSubscribers(18), is(6));
        assertThat(summary.getNewSubscribers(21), is(37));
    }

    private Board makeBoard() {
        StandardCard s1 = new StandardCard("S1", Card.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s1.onSelected(new Context(new Board(), new DaysFactory(true).getDay(6)));
        s1.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(8)));

        StandardCard s2 = new StandardCard("S2", Card.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {1, 3, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s2.onSelected(new Context(new Board(), new DaysFactory(true).getDay(9)));
        s2.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(11)));

        StandardCard s3 = new StandardCard("S3", Card.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {1, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s3.onSelected(new Context(new Board(), new DaysFactory(true).getDay(12)));
        s3.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(14)));

        StandardCard s4 = new StandardCard("S4", Card.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {1, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s4.onSelected(new Context(new Board(), new DaysFactory(true).getDay(14)));
        s4.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(17)));

        Card e1 = Cards.getCard("E1");
        e1.onSelected(new Context(new Board(), new DaysFactory(true).getDay(16)));
        e1.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(18)));

        Card f2 = Cards.getCard("F2");
        f2.onSelected(new Context(new Board(), new DaysFactory(true).getDay(19)));
        f2.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(21)));

        StandardCard s5 = new StandardCard("S5", Card.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {1, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s5.onSelected(new Context(new Board(), new DaysFactory(true).getDay(16)));
        s5.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(20)));

        Board b = new Board();
        DeployedColumn deployed = b.getDeployed();
        deployed.addCard(s1, ClassOfService.STANDARD);
        deployed.addCard(s2, ClassOfService.STANDARD);
        deployed.addCard(s3, ClassOfService.STANDARD);
        deployed.addCard(s4, ClassOfService.STANDARD);
        deployed.addCard(s5, ClassOfService.STANDARD);
        deployed.addCard(e1, ClassOfService.STANDARD);
        deployed.addCard(f2, ClassOfService.STANDARD);
        deployed.addCard(Cards.getCard("F1"), ClassOfService.STANDARD);

        return b;
    }

    @Test
    public void testTotalSubscribersToDate() {

        FinancialSummary summary = new FinancialSummary(makeBoard());
        assertThat(summary.getTotalSubscribersToDate(9), is(2));
        assertThat(summary.getTotalSubscribersToDate(12), is(5));
        assertThat(summary.getTotalSubscribersToDate(15), is(9));
        assertThat(summary.getTotalSubscribersToDate(18), is(15));
        assertThat(summary.getTotalSubscribersToDate(21), is(52));
    }

    @Test
    public void testFinesAndPayments() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getFinesOrPayments(15), is(-1500));
        assertThat(summary.getFinesOrPayments(18), is(4000));
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
        assertThat(summary.getFinesOrPayments(15), is(0));
    }

    @Test
    public void testFineForF1WhenLate() {
        Card f1 = Cards.getCard("F1");
        f1.onSelected(new Context(new Board(), new DaysFactory(true).getDay(10)));
        f1.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(16)));

        Board b = new Board();
        DeployedColumn deployed = b.getDeployed();
        deployed.addCard(f1, ClassOfService.STANDARD);

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

        assertThat(summary.getBillingCycleRevenue(9), is(20));
        assertThat(summary.getBillingCycleRevenue(12), is(75));
        assertThat(summary.getBillingCycleRevenue(15), is(180));
        assertThat(summary.getBillingCycleRevenue(18), is(375));
        assertThat(summary.getBillingCycleRevenue(21), is(1560));
    }

    @Test
    public void testBillingCycleGrossProfit() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getBillingCycleGrossProfit(9), is(20));
        assertThat(summary.getBillingCycleGrossProfit(12), is(75));
        assertThat(summary.getBillingCycleGrossProfit(15), is(-1320));
        assertThat(summary.getBillingCycleGrossProfit(18), is(4375));
        assertThat(summary.getBillingCycleGrossProfit(21), is(1560));
    }

    @Test
    public void testGrossProfitToDate() {
        FinancialSummary summary = new FinancialSummary(makeBoard());

        assertThat(summary.getTotalGrossProfitToDate(9), is(20));
        assertThat(summary.getTotalGrossProfitToDate(12), is(95));
        assertThat(summary.getTotalGrossProfitToDate(15), is(-1225));
        assertThat(summary.getTotalGrossProfitToDate(18), is(3150));
        assertThat(summary.getTotalGrossProfitToDate(21), is(4710));
    }
}
