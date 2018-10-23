package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Board {
    private final List<ActivityDice> dice = new ArrayList<>();
    private final EnumMap<Column.Type, Column> columns = new EnumMap<>(Column.Type.class);

    public List<ActivityDice> getDice() {
        return dice;
    }

    public void addDice(ActivityDice dice) {
        this.dice.add(dice);
    }

    public void setColumn(Column.Type type, ActivityColumn column) {
        columns.put(type, column);
    }

    public Column getColumn(Column.Type type) {
        return columns.get(type);
    }
}
