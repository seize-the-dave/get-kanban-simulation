package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    // Selected (3), Analysis (2), Development (4), Test (3)
    private final List<StateDice> dice = new ArrayList<>();
    private final EnumMap<State, Column> columns = new EnumMap<>(State.class);

    public List<StateDice> getDice() {
        return dice;
    }

    public List<StateDice> getDice(State state) {
        return dice.stream().filter(d -> d.getActivity() == state).collect(Collectors.toList());
    }

    public void addDice(StateDice dice) {
        this.dice.add(dice);
    }

    public void setColumn(State type, Column column) {
        columns.put(type, column);
    }

    public Column getColumn(State type) {
        return columns.get(type);
    }
}
