package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.column.*;
import uk.org.grant.getkanban.dice.RandomDice;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.IntangiblesFirstPrioritisationStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final List<StateDice> dice = new ArrayList<>();

    // Selected (3), Analysis (2), Development (4), Test (3)
    private final Options backlog = new Options(new IntangiblesFirstPrioritisationStrategy().thenComparing(new BusinessValuePrioritisationStrategy()));
    private final SelectedColumn selected = new SelectedColumn(3, backlog);
    private final EnumMap<State, StateColumn> columns = new EnumMap<>(State.class);
    {
        columns.put(State.ANALYSIS, new StateColumn(State.ANALYSIS, 2, selected, backlog));
        columns.put(State.DEVELOPMENT, new StateColumn(State.DEVELOPMENT, 4, columns.get(State.ANALYSIS), columns.get(State.ANALYSIS)));
        columns.put(State.TEST, new StateColumn(State.TEST, 3, columns.get(State.DEVELOPMENT), columns.get(State.DEVELOPMENT)));
    }
    private final ReadyToDeployColumn readyToDeploy = new ReadyToDeployColumn(columns.get(State.TEST));
    private final DeployedColumn deployed = new DeployedColumn(readyToDeploy, columns.get(State.TEST));
    private final Map<Integer, WipLimitAdjustment> adjustments = new HashMap<>();

    public Board() {
        initDice();
        initCards();
    }

    private void initCards() {
        deployed.addCard(Cards.getCard("S1"), ClassOfService.STANDARD);
        deployed.addCard(Cards.getCard("S2"), ClassOfService.STANDARD);
        deployed.addCard(Cards.getCard("S4"), ClassOfService.STANDARD);
        getStateColumn(State.TEST).addCard(Cards.getCard("S3"), ClassOfService.STANDARD);
        getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S5"), ClassOfService.STANDARD);
        getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S6"), ClassOfService.STANDARD);
        getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S7"), ClassOfService.STANDARD);
        getStateColumn(State.DEVELOPMENT).addCard(Cards.getCard("S9"), ClassOfService.STANDARD);
        getStateColumn(State.ANALYSIS).addCard(Cards.getCard("S8"), ClassOfService.STANDARD);
        getStateColumn(State.ANALYSIS).addCard(Cards.getCard("S10"), ClassOfService.STANDARD);
        selected.addCard(Cards.getCard("S13"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S11"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S12"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S14"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S15"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S16"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S17"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("S18"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("F1"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("F2"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("I1"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("I2"), ClassOfService.STANDARD);
        backlog.addCard(Cards.getCard("I3"), ClassOfService.STANDARD);
    }

    private void initDice() {
        addDice(new StateDice(State.ANALYSIS, new RandomDice()));
        addDice(new StateDice(State.ANALYSIS, new RandomDice()));
        addDice(new StateDice(State.DEVELOPMENT, new RandomDice()));
        addDice(new StateDice(State.DEVELOPMENT, new RandomDice()));
        addDice(new StateDice(State.DEVELOPMENT, new RandomDice()));
        addDice(new StateDice(State.TEST, new RandomDice()));
        addDice(new StateDice(State.TEST, new RandomDice()));
    }

    public List<StateDice> getDice() {
        return dice;
    }

    public List<StateDice> getDice(State state) {
        return dice.stream().filter(d -> d.getActivity() == state).collect(Collectors.toList());
    }

    public void addDice(StateDice dice) {
        this.dice.add(dice);
    }

    public void removeDice(StateDice dice) {
        this.dice.remove(dice);
    }

    public StateColumn getStateColumn(State type) {
        return columns.get(type);
    }

    public Options getOptions() {
        return this.backlog;
    }

    public SelectedColumn getSelected() {
        return this.selected;
    }

    public ReadyToDeployColumn getReadyToDeploy() {
        return this.readyToDeploy;
    }

    public DeployedColumn getDeployed() {
        return this.deployed;
    }

    public void putAdjustment(WipLimitAdjustment adjustment) {
        if (this.adjustments.size() < 3) {
            this.adjustments.put(adjustment.getDay(), adjustment);
        } else {
            throw new IllegalStateException("Too many WIP adjustments");
        }
    }

    public Collection<Card> getCards() {
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(backlog.getCards());
        cards.addAll(selected.getCards());
        cards.addAll(columns.get(State.ANALYSIS).getCards());
        cards.addAll(columns.get(State.DEVELOPMENT).getCards());
        cards.addAll(columns.get(State.TEST).getCards());
        cards.addAll(readyToDeploy.getCards());
        cards.addAll(deployed.getCards());

        return cards;
    }

    public void adjustLimits(int ordinal) {
        if (adjustments.containsKey(ordinal)) {
            WipLimitAdjustment adjustment = adjustments.get(ordinal);

            selected.setLimit(adjustment.getSelected());
            columns.get(State.ANALYSIS).setLimit(adjustment.getAnalysis());
            columns.get(State.DEVELOPMENT).setLimit(adjustment.getDevelopment());
            columns.get(State.TEST).setLimit(adjustment.getTest());
        }
    }

    /**
     * Remove all the cards from the board
     */
    public void clear() {
        clearDice();
        clearCardsFromColumns();
    }

    private void clearCardsFromColumns() {
        backlog.clear();
        selected.clear();
        columns.get(State.ANALYSIS).clear();
        columns.get(State.DEVELOPMENT).clear();
        columns.get(State.TEST).clear();
        readyToDeploy.clear();
        deployed.clear();
    }

    private void clearDice() {
        dice.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("Backlog\n");
        sb.append("-------\n");
        for (Card card : backlog.getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Options\n");
        sb.append("-------\n");
        for (Card card : selected.getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Analysis\n");
        sb.append("--------\n");
        for (Card card : columns.get(State.ANALYSIS).getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Development\n");
        sb.append("-----------\n");
        for (Card card : columns.get(State.DEVELOPMENT).getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Test\n");
        sb.append("----\n");
        for (Card card : columns.get(State.TEST).getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Ready to Deploy\n");
        sb.append("---------------\n");
        for (Card card : readyToDeploy.getCards()) {
            sb.append("-" + card + "\n");
        }
        sb.append("Deployed\n");
        sb.append("--------\n");
        for (Card card : deployed.getCards()) {
            sb.append("-" + card + "\n");
        }
        return sb.toString();
    }
}
