package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    // Selected (3), Analysis (2), Development (4), Test (3)
    private final List<ActivityDice> dice = new ArrayList<>();
    private final EnumMap<State, Column> columns = new EnumMap<>(State.class);

    public List<ActivityDice> getDice() {
        return dice;
    }

    public List<ActivityDice> getDice(State state) {
        return dice.stream().filter(d -> d.getActivity() == state).collect(Collectors.toList());
    }

    public void addDice(ActivityDice dice) {
        this.dice.add(dice);
    }

    public void setColumn(State type, Column column) {
        columns.put(type, column);
    }

    public Column getColumn(State type) {
        return columns.get(type);
    }
}
