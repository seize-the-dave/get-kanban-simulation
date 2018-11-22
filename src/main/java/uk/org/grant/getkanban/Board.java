package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.*;
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
        columns.put(State.ANALYSIS, new StateColumn(State.ANALYSIS, 2, selected));
        columns.put(State.DEVELOPMENT, new StateColumn(State.DEVELOPMENT, 4, columns.get(State.ANALYSIS)));
        columns.put(State.TEST, new StateColumn(State.TEST, 3, columns.get(State.DEVELOPMENT)));
    }
    private final ReadyToDeployColumn readyToDeploy = new ReadyToDeployColumn(columns.get(State.TEST));
    private final DeployedColumn deployed = new DeployedColumn(readyToDeploy);

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
}
