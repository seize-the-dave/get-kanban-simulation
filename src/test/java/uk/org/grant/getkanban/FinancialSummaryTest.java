package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FinancialSummaryTest {
    @Test
    public void testNewSubscribersThisBillingCycle() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getNewSubscribers(9), is(2));
        assertThat(summary.getNewSubscribers(12), is(3));
        assertThat(summary.getNewSubscribers(15), is(4));
        assertThat(summary.getNewSubscribers(18), is(6));
        assertThat(summary.getNewSubscribers(21), is(37));
    }

    private Column buildDeployedColumn() {
        Card s1 = new Card("S1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s1.setDaySelected(1);
        s1.setDayDeployed(3);

        Card s2 = new Card("S2", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {1, 3, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s2.setDaySelected(9);
        s2.setDayDeployed(11);

        Card s3 = new Card("S3", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {1, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s3.setDaySelected(12);
        s3.setDayDeployed(14);

        Card s4 = new Card("S4", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {1, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s4.setDaySelected(14);
        s4.setDayDeployed(17);

        Card e1 = new Card("E1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        e1.setDaySelected(16);
        e1.setDayDeployed(18);

        Card f2 = new Card("F2", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        f2.setDaySelected(19);
        f2.setDayDeployed(21);

        Card s5 = new Card("S5", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {1, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        s5.setDaySelected(16);
        s5.setDayDeployed(20);

        Column deployed = new DeployedColumn(new NullColumn());
        deployed.addCard(s1);
        deployed.addCard(s2);
        deployed.addCard(s3);
        deployed.addCard(s4);
        deployed.addCard(s5);
        deployed.addCard(e1);
        deployed.addCard(f2);

        return deployed;
    }

    @Test
    public void testTotalSubscribersToDate() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getTotalSubscribersToDate(9), is(2));
        assertThat(summary.getTotalSubscribersToDate(12), is(5));
        assertThat(summary.getTotalSubscribersToDate(15), is(9));
        assertThat(summary.getTotalSubscribersToDate(18), is(15));
        assertThat(summary.getTotalSubscribersToDate(21), is(52));
    }

    @Test
    public void testFinesAndPayments() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getFinesOrPayments(15), is(-1500));
        assertThat(summary.getFinesOrPayments(18), is(4000));
    }

    @Test
    public void testNoFineForF1WhenDelivered() {
        Card f1 = new Card("F1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        f1.setDaySelected(10);
        f1.setDayDeployed(15);

        Column deployed = new DeployedColumn(new NullColumn());
        deployed.addCard(f1);

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getFinesOrPayments(15), is(0));
    }

    @Test
    public void testFineForF1WhenLate() {
        Card f1 = new Card("F1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        f1.setDaySelected(10);
        f1.setDayDeployed(16);

        Column deployed = new DeployedColumn(new NullColumn());
        deployed.addCard(f1);

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getFinesOrPayments(15), is(-1500));
    }

    @Test
    public void testNoPaymentForE1WhenLate() {
        Card e1 = new Card("E1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        e1.setDaySelected(15);
        e1.setDayDeployed(19);

        Column deployed = new DeployedColumn(new NullColumn());
        deployed.addCard(e1);

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getFinesOrPayments(18), is(0));
    }

    @Test
    public void testBillingCycleRevenue() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getBillingCycleRevenue(9), is(20));
        assertThat(summary.getBillingCycleRevenue(12), is(75));
        assertThat(summary.getBillingCycleRevenue(15), is(180));
        assertThat(summary.getBillingCycleRevenue(18), is(375));
        assertThat(summary.getBillingCycleRevenue(21), is(1560));
    }

    @Test
    public void testBillingCycleGrossProfit() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getBillingCycleGrossProfit(9), is(20));
        assertThat(summary.getBillingCycleGrossProfit(12), is(75));
        assertThat(summary.getBillingCycleGrossProfit(15), is(-1320));
        assertThat(summary.getBillingCycleGrossProfit(18), is(4375));
        assertThat(summary.getBillingCycleGrossProfit(21), is(1560));
    }

    @Test
    public void testGrossProfitToDate() {
        Column deployed = buildDeployedColumn();

        FinancialSummary summary = new FinancialSummary(deployed);
        assertThat(summary.getTotalGrossProfitToDate(9), is(20));
        assertThat(summary.getTotalGrossProfitToDate(12), is(95));
        assertThat(summary.getTotalGrossProfitToDate(15), is(-1225));
        assertThat(summary.getTotalGrossProfitToDate(18), is(3150));
        assertThat(summary.getTotalGrossProfitToDate(21), is(4710));
    }
}
