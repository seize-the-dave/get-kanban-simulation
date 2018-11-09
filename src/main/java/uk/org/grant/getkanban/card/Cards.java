package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.VariableSubscriberProfile;

import java.util.HashMap;
import java.util.Map;

public class Cards {
    private final Map<String, StandardCard> cards = new HashMap<>();
    {
        cards.put("S1", new StandardCard("S1", StandardCard.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {9, 9, 7, 7, 7, 6, 5, 5, 5, 3, 0, -2, -4, -4, -5}), 1, 9));
        cards.put("S2", new StandardCard("S2", StandardCard.Size.LOW, 0, 0, 0, new VariableSubscriberProfile(new int[] {10, 9, 7, 7, 5, 4, 4, 4, 4, 3, 3, 2, 2, 1, 0}), 1, 9));
        cards.put("S3", new StandardCard("S3", StandardCard.Size.MEDIUM, 0, 0, 6, new VariableSubscriberProfile(new int[] {14, 14, 14, 14, 13, 13, 12, 10, 8, 8, 8, 5, 4, 2, 1}), 2));
        cards.put("S4", new StandardCard("S4", StandardCard.Size.HIGH, 0, 0, 0, new VariableSubscriberProfile(new int[] {16, 14, 14, 13, 11, 11, 11, 11, 11, 11, 11, 10, 9, 8, 7}), 3, 9));
        cards.put("S5", new StandardCard("S5", StandardCard.Size.MEDIUM, 0, 0, 9, new VariableSubscriberProfile(new int[] {13, 13, 12, 12, 12, 11, 11, 8, 8, 8, 6, 6, 6, 6, 4}), 3));
        cards.put("S6", new StandardCard("S6", StandardCard.Size.HIGH, 0, 2, 8, new VariableSubscriberProfile(new int[] {17, 16, 16, 16, 16, 16, 14, 14, 14, 13, 13, 13, 12, 12, 9}), 4));
        cards.put("S7", new StandardCard("S7", StandardCard.Size.HIGH, 0, 5, 8, new VariableSubscriberProfile(new int[] {16, 16, 16, 14, 13, 12, 11, 11, 10, 9, 7, 7, 7, 6, 5}), 5));
        cards.put("S8", new StandardCard("S8", StandardCard.Size.MEDIUM, 1, 8, 9, new VariableSubscriberProfile(new int[] {12, 11, 11, 10, 9, 9, 7, 6, 5, 4, 4, 4, 1, 1, -2}), 6));
        cards.put("S9", new StandardCard("S9", StandardCard.Size.MEDIUM, 0, 4, 7, new VariableSubscriberProfile(new int[] {13, 10, 10, 8, 6, 6, 6, 4, 4, 4, 4, 2, 1, 0, -1}), 6));
        cards.put("S10", new StandardCard("S10", StandardCard.Size.HIGH, 1, 6, 9, new VariableSubscriberProfile(new int[] {17, 16, 15, 15, 14, 14, 14, 14, 14, 14, 11, 11, 10, 7, 4})));
        cards.put("S11", new StandardCard("S11", StandardCard.Size.MEDIUM, 3, 4, 9, new VariableSubscriberProfile(new int[] {13, 13, 11, 11, 11, 11, 10, 10, 10, 10, 10, 8, 8, 5, 4})));
        cards.put("S12", new StandardCard("S12", StandardCard.Size.HIGH, 5, 6, 10, new VariableSubscriberProfile(new int[] {18, 16, 15, 15, 15, 14, 11, 10, 10, 9, 7, 6, 6, 6, 4})));
        cards.put("S13", new StandardCard("S13", StandardCard.Size.HIGH, 3, 3, 8, new VariableSubscriberProfile(new int[] {16, 16, 14, 13, 12, 11, 11, 10, 8, 8, 5, 4, 4, 3, 1}), 9));
        cards.put("S14", new StandardCard("S14", StandardCard.Size.HIGH, 3, 4, 9, new VariableSubscriberProfile(new int[] {14, 13, 13, 11, 10, 10, 7, 7, 7, 7, 5, 5, 4, 3, 2})));
        cards.put("S15", new StandardCard("S15", StandardCard.Size.MEDIUM, 3, 3, 9, new VariableSubscriberProfile(new int[] {13, 12, 11, 11, 11, 11, 11, 9, 8, 6, 6, 4, 3, 2, 1})));
        cards.put("S16", new StandardCard("S16", StandardCard.Size.MEDIUM, 2, 5, 5, new VariableSubscriberProfile(new int[] {11, 11, 10, 10, 10, 9, 9, 8, 8, 6, 6, 4, 3, 2, -1})));
        cards.put("S17", new StandardCard("S17", StandardCard.Size.MEDIUM, 5, 8, 6, new VariableSubscriberProfile(new int[] {12, 11, 10, 10, 10, 10, 9, 8, 8, 6, 6, 4, 3, 3, -1})));
        cards.put("S18", new StandardCard("S18", StandardCard.Size.HIGH, 6, 7, 5, new VariableSubscriberProfile(new int[] {16, 16, 14, 14, 14, 13, 11, 11, 11, 10, 8, 8, 7, 7, 5})));
        cards.put("S19", new StandardCard("S19", StandardCard.Size.MEDIUM, 5, 7, 2, new VariableSubscriberProfile(new int[] {13, 11, 8, 8, 8, 7, 7, 6, 6, 6, 5, 3, 1, 0, -4})));
        cards.put("S20", new StandardCard("S20", StandardCard.Size.LOW, 4, 4, 4, new VariableSubscriberProfile(new int[] {10, 10, 7, 7, 6, 5, 5, 5, 4, 4, 3, 3, 3, 3, 1})));
        cards.put("S21", new StandardCard("S21", StandardCard.Size.HIGH, 5, 5, 7, new VariableSubscriberProfile(new int[] {18, 15, 15, 15, 15, 15, 14, 14, 12, 12, 12, 12, 11, 11, 8})));
        cards.put("S22", new StandardCard("S22", StandardCard.Size.HIGH, 8, 4, 5, new VariableSubscriberProfile(new int[] {17, 16, 16, 15, 15, 15, 14, 12, 12, 11, 11, 10, 10, 8, 7})));
        cards.put("S23", new StandardCard("S23", StandardCard.Size.LOW, 3, 7, 4, new VariableSubscriberProfile(new int[] {10, 10, 10, 10, 8, 7, 7, 7, 5, 3, 2, 2, 2, 2, 1})));
        cards.put("S24", new StandardCard("S24", StandardCard.Size.HIGH, 4, 7, 4, new VariableSubscriberProfile(new int[] {16, 16, 15, 14, 14, 13, 11, 10, 10, 10, 8, 8, 7, 7, 6})));
        cards.put("S25", new StandardCard("S25", StandardCard.Size.MEDIUM, 4, 7, 7, new VariableSubscriberProfile(new int[] {12, 11, 10, 10, 10, 10, 9, 8, 7, 6, 6, 4, 3, 3, 1})));
        cards.put("S26", new StandardCard("S26", StandardCard.Size.MEDIUM, 5, 7, 4, new VariableSubscriberProfile(new int[] {11, 11, 11, 10, 9, 9, 7, 6, 5, 4, 4, 4, 3, 1, -2})));
        cards.put("S27", new StandardCard("S27", StandardCard.Size.HIGH, 7, 5, 5, new VariableSubscriberProfile(new int[] {16, 16, 15, 14, 14, 13, 12, 11, 10, 10, 9, 8, 7, 7, 6})));
        cards.put("S28", new StandardCard("S28", StandardCard.Size.MEDIUM, 3, 6, 2, new VariableSubscriberProfile(new int[] {13, 13, 12, 11, 11, 11, 10, 10, 10, 10, 9, 8, 7, 5, 2})));
        cards.put("S29", new StandardCard("S29", StandardCard.Size.VERY_HIGH, 4, 6, 4, new VariableSubscriberProfile(new int[] {19, 18, 17, 15, 15, 14, 13, 11, 9, 7, 4, 4, 1, 1, -1})));
        cards.put("S30", new StandardCard("S30", StandardCard.Size.HIGH, 3, 4, 3, new VariableSubscriberProfile(new int[] {17, 15, 15, 13, 13, 12, 12, 12, 10, 9, 7, 4, 3, 2, -2})));
        cards.put("S31", new StandardCard("S31", StandardCard.Size.VERY_HIGH, 5, 6, 3, new VariableSubscriberProfile(new int[] {21, 21, 21, 19, 18, 18, 16, 14, 14, 12, 11, 11, 11, 9, 8})));
        cards.put("S32", new StandardCard("S32", StandardCard.Size.HIGH, 4, 7, 4, new VariableSubscriberProfile(new int[] {16, 16, 16, 16, 16, 15, 15, 15, 14, 12, 11, 11, 11, 11, 10})));
        cards.put("S33", new StandardCard("S33", StandardCard.Size.HIGH, 4, 7, 4, new VariableSubscriberProfile(new int[] {16, 16, 16, 16, 16, 15, 15, 15, 14, 12, 11, 11, 11, 11, 10})));
        cards.put("E1", new StandardCard("E1", StandardCard.Size.NONE, 4, 6, 4, new FixedSubscriberProfile()));
        cards.put("E2", new StandardCard("E2", StandardCard.Size.NONE, 2, 3, 4, new FixedSubscriberProfile(-6)));
        cards.put("I1", new StandardCard("I1", StandardCard.Size.NONE, 1, 4, 2, new FixedSubscriberProfile()));
        cards.put("I2", new StandardCard("I2", StandardCard.Size.NONE, 2, 2, 5, new FixedSubscriberProfile()));
        cards.put("I3", new StandardCard("I3", StandardCard.Size.NONE, 1, 3, 3, new FixedSubscriberProfile()));
        cards.put("F1", new StandardCard("F1", StandardCard.Size.NONE, 4, 3, 6, new FixedSubscriberProfile()));
        cards.put("F2", new StandardCard("F2", StandardCard.Size.NONE, 5, 6, 4, new FixedSubscriberProfile()));
    }

    public static StandardCard getCard(String name) {
        Cards cards = new Cards();
        return cards.createCard(name);
    }

    public StandardCard createCard(String name) {
        if (cards.containsKey(name)) {
            return cards.get(name);
        }
        throw new IllegalArgumentException();
    }
}
