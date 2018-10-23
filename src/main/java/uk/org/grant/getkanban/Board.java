package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Board {
    // Selected (3), Analysis (2), Development (4), Test (3)
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

//    public static Board createBoard() {
//        Board board = new Board();
//        board.setColumn(Column.Type.READY_TO_DEPLOY, null);
//        Column analysis = new ActivityColumn(Activity.ANALYSIS, null);
//        Column development = new ActivityColumn(Activity.DEVELOPMENT, analysis);
//        Column test = new ActivityColumn(Activity.TEST, development);
//
//        Card s1 = new Card(Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {9,9,7,7,7,6,5,5,5,3,0,-2,-4,-4,-5}));
//        s1.setDaySelected(1);
//
//        Card s2 = new Card(Card.Size.LOW, 0, 0, 2, new SubscriberProfile(new int[] {10,9,7,7,5,4,4,4,4,3,3,2,2,1,0}));
//        s2.setDaySelected(1);
//        test.addCard(s2);
//
//        Card s3 = new Card(Card.Size.MEDIUM, 0, 0, 9, new SubscriberProfile(new int[] {}));
//        s3.setDaySelected(2);
//        development.addCard(s3);
//
//        Card s4 = new Card(Card.Size.HIGH, 0, 0, 4, new SubscriberProfile(new int[] {}));
//        s4.setDaySelected(3);
//        development.addCard(s4);
//
//        Card s5 = new Card(Card.Size.MEDIUM, 0, 0, 9, new SubscriberProfile(new int[] {}));
//        s5.setDaySelected(3);
//        development.addCard(s5);
//
//        Card s6 = new Card(Card.Size.MEDIUM, 0, 6, 8, new SubscriberProfile(new int[] {}));
//        s6.setDaySelected(4);
//        development.addCard(s6);
//
//        Card s7 = new Card(Card.Size.HIGH, 0, 9, 8, new SubscriberProfile(new int[] {}));
//        s7.setDaySelected(5);
//        analysis.addCard(s7);
//
//        Card s8 = new Card(Card.Size.MEDIUM, 6, 8, 9, new SubscriberProfile(new int[] {}));
//        s8.setDaySelected(6);
//        analysis.addCard(s8);
//
//        Card s9 = new Card(Card.Size.MEDIUM, 3, 4, 7, new SubscriberProfile(new int[] {}));
//        s9.setDaySelected(6);
//        analysis.addCard(s8);
//
//
//        return board;
//    }
}
