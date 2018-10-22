package uk.org.grant.getkanban.dice;

public class LoadedDice implements Dice {
    private final int load;

    public LoadedDice(int load) {
        this.load = load;
    }

    @Override
    public int roll() {
        return load;
    }
}
