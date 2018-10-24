package uk.org.grant.getkanban;

public class CardFactory {
    public static Card getCard(String name) {
        switch (name) {
            case "S1":
                Card s1 = new Card("S1", Card.Size.LOW, 0, 0, 0, new SubscriberProfile(new int[] {9, 9, 7, 7, 7, 6, 5, 5, 5, 3, 0, -2, -4, -4, -5}));
                s1.setDaySelected(1);
                return s1;
            case "S2":
                Card s2 = new Card("S2", Card.Size.LOW, 0, 0, 2, new SubscriberProfile(new int[] {10, 9, 7, 7, 5, 4, 4, 4, 4, 3, 3, 2, 2, 1, 0}));
                s2.setDaySelected(1);
                return s2;
            case "S3":
                Card s3 = new Card("S3", Card.Size.MEDIUM, 0, 0, 9, new SubscriberProfile(new int[] {14, 14, 14, 14, 13, 13, 12, 10, 8, 8, 8, 5, 4, 2, 1}));
                s3.setDaySelected(2);
                return s3;
            case "S4":
                Card s4 = new Card("S4", Card.Size.HIGH, 0, 0, 4, new SubscriberProfile(new int[] {16, 14, 14, 13, 11, 11, 11, 11, 11, 11, 11, 10, 9, 8, 7}));
                s4.setDaySelected(3);
                return s4;
            case "S5":
                Card s5 = new Card("S5", Card.Size.MEDIUM, 0, 0, 9, new SubscriberProfile(new int[] {13, 13, 12, 12, 12, 11, 11, 8, 8, 8, 6, 6, 6, 6, 4}));
                s5.setDaySelected(3);
                return s5;
            case "S6":
                Card s6 = new Card("S6", Card.Size.HIGH, 0, 7, 8, new SubscriberProfile(new int[] {17, 16, 16, 16, 16, 16, 14, 14, 14, 13, 13, 13, 12, 12, 9}));
                s6.setDaySelected(4);
                return s6;
            case "S7":
                Card s7 = new Card("S7", Card.Size.HIGH, 0, 9, 8, new SubscriberProfile(new int[] {16, 16, 16, 14, 13, 12, 11, 11, 10, 9, 7, 7, 7, 6, 5}));
                s7.setDaySelected(5);
                return s7;
            case "S8":
                Card s8 = new Card("S8", Card.Size.MEDIUM, 6, 8, 9, new SubscriberProfile(new int[] {12, 11, 11, 10, 9, 9, 7, 6, 5, 4, 4, 4, 1, 1, -2}));
                s8.setDaySelected(6);
                return s8;
            case "S9":
                Card s9 = new Card("S9", Card.Size.MEDIUM, 3, 4, 7, new SubscriberProfile(new int[] {13, 10, 10, 8, 6, 6, 6, 4, 4, 4, 4, 2, 1, 0, -1}));
                s9.setDaySelected(6);
                return s9;
            case "S10":
                Card s10 = new Card("S10", Card.Size.HIGH, 2, 6, 9, new SubscriberProfile(new int[] {17, 16, 15, 15, 14, 14, 14, 14, 14, 14, 11, 11, 10, 7, 4}));
                s10.setDaySelected(7);
                return s10;
            case "S11":
                return new Card("S11", Card.Size.MEDIUM, 3, 4, 9, new SubscriberProfile(new int[] {13, 13, 11, 11, 11, 11, 10, 10, 10, 10, 10, 8, 8, 5, 4}));
            case "S12":
                return new Card("S12", Card.Size.HIGH, 5, 6, 10, new SubscriberProfile(new int[] {18, 16, 15, 15, 15, 14, 11, 10, 10, 9, 7, 6, 6, 6, 4}));
            case "S13":
                return new Card("S14", Card.Size.HIGH, 3, 3, 8, new SubscriberProfile(new int[] {16, 16, 14, 13, 12, 11, 11, 10, 8, 8, 5, 4, 4, 3, 1}));
            case "S14":
                return new Card("S14", Card.Size.HIGH, 3, 4, 9, new SubscriberProfile(new int[] {14, 13, 13, 11, 10, 10, 7, 7, 7, 7, 5, 5, 4, 3, 2}));
        }
        throw new IllegalArgumentException();
    }
}
