package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.FixedSubscriberProfile;
import uk.org.grant.getkanban.SubscriberProfile;

import java.util.HashMap;
import java.util.Map;

public class Cards {
    private final Map<String, Card> cards = new HashMap<>();
    {
        Card s1 = new Card("S1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {9, 9, 7, 7, 7, 6, 5, 5, 5, 3, 0, -2, -4, -4, -5}));
        s1.setDaySelected(1);
        s1.setDayDeployed(9);
        cards.put("S1", s1);

        Card s2 = new Card("S2", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {10, 9, 7, 7, 5, 4, 4, 4, 4, 3, 3, 2, 2, 1, 0}));
        s2.setDaySelected(1);
        s2.setDayDeployed(9);
        cards.put("S2", s2);

        Card s3 = new Card("S3", Card.Size.MEDIUM, 0, 0, 6, new SubscriberProfile(new int[] {14, 14, 14, 14, 13, 13, 12, 10, 8, 8, 8, 5, 4, 2, 1}));
        s3.setDaySelected(2);
        cards.put("S3", s3);

        Card s4 = new Card("S4", Card.Size.HIGH, 0, 0, 0, new SubscriberProfile(new int[] {16, 14, 14, 13, 11, 11, 11, 11, 11, 11, 11, 10, 9, 8, 7}));
        s4.setDaySelected(3);
        s4.setDayDeployed(9);
        cards.put("S4", s4);

        Card s5 = new Card("S5", Card.Size.MEDIUM, 0, 0, 9, new SubscriberProfile(new int[] {13, 13, 12, 12, 12, 11, 11, 8, 8, 8, 6, 6, 6, 6, 4}));
        s5.setDaySelected(3);
        cards.put("S5", s5);

        Card s6 = new Card("S6", Card.Size.HIGH, 0, 2, 8, new SubscriberProfile(new int[] {17, 16, 16, 16, 16, 16, 14, 14, 14, 13, 13, 13, 12, 12, 9}));
        s6.setDaySelected(4);
        cards.put("S6", s6);

        Card s7 = new Card("S7", Card.Size.HIGH, 0, 5, 8, new SubscriberProfile(new int[] {16, 16, 16, 14, 13, 12, 11, 11, 10, 9, 7, 7, 7, 6, 5}));
        s7.setDaySelected(5);
        cards.put("S7", s7);

        Card s8 = new Card("S8", Card.Size.MEDIUM, 1, 8, 9, new SubscriberProfile(new int[] {12, 11, 11, 10, 9, 9, 7, 6, 5, 4, 4, 4, 1, 1, -2}));
        s8.setDaySelected(6);
        cards.put("S8", s8);

        Card s9 = new Card("S9", Card.Size.MEDIUM, 0, 4, 7, new SubscriberProfile(new int[] {13, 10, 10, 8, 6, 6, 6, 4, 4, 4, 4, 2, 1, 0, -1}));
        s9.setDaySelected(6);
        cards.put("S9", s9);

        Card s10 = new Card("S10", Card.Size.HIGH, 1, 6, 9, new SubscriberProfile(new int[] {17, 16, 15, 15, 14, 14, 14, 14, 14, 14, 11, 11, 10, 7, 4}));
        s10.setDaySelected(7);
        cards.put("S10", s10);

        cards.put("S11", new Card("S11", Card.Size.MEDIUM, 3, 4, 9, new SubscriberProfile(new int[] {13, 13, 11, 11, 11, 11, 10, 10, 10, 10, 10, 8, 8, 5, 4})));
        cards.put("S12", new Card("S12", Card.Size.HIGH, 5, 6, 10, new SubscriberProfile(new int[] {18, 16, 15, 15, 15, 14, 11, 10, 10, 9, 7, 6, 6, 6, 4})));

        Card s13 = new Card("S13", Card.Size.HIGH, 3, 3, 8, new SubscriberProfile(new int[] {16, 16, 14, 13, 12, 11, 11, 10, 8, 8, 5, 4, 4, 3, 1}));
        s13.setDaySelected(9);
        cards.put("S13", s13);

        cards.put("S14", new Card("S14", Card.Size.HIGH, 3, 4, 9, new SubscriberProfile(new int[] {14, 13, 13, 11, 10, 10, 7, 7, 7, 7, 5, 5, 4, 3, 2})));
        cards.put("S15", new Card("S15", Card.Size.MEDIUM, 3, 3, 9, new SubscriberProfile(new int[] {13, 12, 11, 11, 11, 11, 11, 9, 8, 6, 6, 4, 3, 2, 1})));
        cards.put("S16", new Card("S16", Card.Size.MEDIUM, 2, 5, 5, new SubscriberProfile(new int[] {11, 11, 10, 10, 10, 9, 9, 8, 8, 6, 6, 4, 3, 2, -1})));
        cards.put("S17", new Card("S17", Card.Size.MEDIUM, 5, 8, 6, new SubscriberProfile(new int[] {12, 11, 10, 10, 10, 10, 9, 8, 8, 6, 6, 4, 3, 3, -1})));
        cards.put("S18", new Card("S18", Card.Size.HIGH, 6, 7, 5, new SubscriberProfile(new int[] {16, 16, 14, 14, 14, 13, 11, 11, 11, 10, 8, 8, 7, 7, 5})));
        cards.put("S19", new Card("S19", Card.Size.MEDIUM, 5, 7, 2, new SubscriberProfile(new int[] {13, 11, 8, 8, 8, 7, 7, 6, 6, 6, 5, 3, 1, 0, -4})));
        cards.put("S20", new Card("S20", Card.Size.LOW, 4, 4, 4, new SubscriberProfile(new int[] {10, 10, 7, 7, 6, 5, 5, 5, 4, 4, 3, 3, 3, 3, 1})));
        cards.put("S21", new Card("S21", Card.Size.HIGH, 5, 5, 7, new SubscriberProfile(new int[] {18, 15, 15, 15, 15, 15, 14, 14, 12, 12, 12, 12, 11, 11, 8})));
        cards.put("S22", new Card("S22", Card.Size.HIGH, 8, 4, 5, new SubscriberProfile(new int[] {17, 16, 16, 15, 15, 15, 14, 12, 12, 11, 11, 10, 10, 8, 7})));
        cards.put("S23", new Card("S23", Card.Size.LOW, 3, 7, 4, new SubscriberProfile(new int[] {10, 10, 10, 10, 8, 7, 7, 7, 5, 3, 2, 2, 2, 2, 1})));
        cards.put("S24", new Card("S24", Card.Size.HIGH, 4, 7, 4, new SubscriberProfile(new int[] {16, 16, 15, 14, 14, 13, 11, 10, 10, 10, 8, 8, 7, 7, 6})));
        cards.put("S25", new Card("S25", Card.Size.MEDIUM, 4, 7, 7, new SubscriberProfile(new int[] {12, 11, 10, 10, 10, 10, 9, 8, 7, 6, 6, 4, 3, 3, 1})));
        cards.put("S26", new Card("S26", Card.Size.MEDIUM, 5, 7, 4, new SubscriberProfile(new int[] {11, 11, 11, 10, 9, 9, 7, 6, 5, 4, 4, 4, 3, 1, -2})));
        cards.put("S27", new Card("S27", Card.Size.HIGH, 7, 5, 5, new SubscriberProfile(new int[] {16, 16, 15, 14, 14, 13, 12, 11, 10, 10, 9, 8, 7, 7, 6})));
        cards.put("S28", new Card("S28", Card.Size.MEDIUM, 3, 6, 2, new SubscriberProfile(new int[] {13, 13, 12, 11, 11, 11, 10, 10, 10, 10, 9, 8, 7, 5, 2})));
        cards.put("S29", new Card("S29", Card.Size.VERY_HIGH, 4, 6, 4, new SubscriberProfile(new int[] {19, 18, 17, 15, 15, 14, 13, 11, 9, 7, 4, 4, 1, 1, -1})));
        cards.put("S30", new Card("S30", Card.Size.HIGH, 3, 4, 3, new SubscriberProfile(new int[] {17, 15, 15, 13, 13, 12, 12, 12, 10, 9, 7, 4, 3, 2, -2})));
        cards.put("S31", new Card("S31", Card.Size.VERY_HIGH, 5, 6, 3, new SubscriberProfile(new int[] {21, 21, 21, 19, 18, 18, 16, 14, 14, 12, 11, 11, 11, 9, 8})));
        cards.put("S32", new Card("S32", Card.Size.HIGH, 4, 7, 4, new SubscriberProfile(new int[] {16, 16, 16, 16, 16, 15, 15, 15, 14, 12, 11, 11, 11, 11, 10})));
        cards.put("S33", new Card("S33", Card.Size.HIGH, 4, 7, 4, new SubscriberProfile(new int[] {16, 16, 16, 16, 16, 15, 15, 15, 14, 12, 11, 11, 11, 11, 10})));

        cards.put("E1", new Card("E1", Card.Size.NONE, 4, 6, 4, new FixedSubscriberProfile()));
        cards.put("E2", new Card("E2", Card.Size.NONE, 2, 3, 4, new FixedSubscriberProfile(-6)));
        cards.put("I1", new Card("I1", Card.Size.NONE, 1, 4, 2, new FixedSubscriberProfile()));
        cards.put("I2", new Card("I2", Card.Size.NONE, 2, 2, 5, new FixedSubscriberProfile()));
        cards.put("I3", new Card("I3", Card.Size.NONE, 1, 3, 3, new FixedSubscriberProfile()));
        cards.put("F1", new Card("F1", Card.Size.NONE, 4, 3, 6, new FixedSubscriberProfile()));
        cards.put("F2", new Card("F2", Card.Size.NONE, 5, 6, 4, new FixedSubscriberProfile()));
    }

    public static Card getCard(String name) {
        Cards cards = new Cards();
        return cards.createCard(name);
    }

    public Card createCard(String name) {
        if (cards.containsKey(name)) {
            return cards.get(name);
        }
        throw new IllegalArgumentException();
    }
}
