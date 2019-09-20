package uk.org.grant.getkanban;

public class WipLimitAdjustment {
    private final int expedite;
    private final int selected;
    private final int analysis;
    private final int development;
    private final int test;
    private final int day;

    public WipLimitAdjustment(int day, int expedite, int selected, int analysis, int development, int test) {
        this.day = day;
        this.expedite = expedite;
        this.selected = selected;
        this.analysis = analysis;
        this.development = development;
        this.test = test;
    }
    
    public int getDay() {
        return this.day;
    }

    public int getExpedite() {
        return this.expedite;
    }

    public int getSelected() {
        return this.selected;
    }

    public int getAnalysis() {
        return this.analysis;
    }

    public int getDevelopment() {
        return this.development;
    }

    public int getTest() {
        return this.test;
    }

    public String toString() {
        return "[EXP=" + expedite + "; SEL=" + selected + "; ANA=" + analysis + "; DEV=" + development + "; TST=" + test + "]";
    }
}
