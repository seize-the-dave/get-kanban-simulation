package uk.org.grant.getkanban;

public class CardFactory {
    public static Card getCard(String name) {
        switch (name) {
            case "S1":
                return new Card("S1", Card.Size.LOW, 0, 1, 1, new SubscriberProfile(new int[] {1, 2, 3}));
            case "S2":
                return new Card("S2", Card.Size.LOW, 5, 2, 1, new SubscriberProfile(new int[] {1, 2, 3}));
        }
        throw new IllegalArgumentException();
    }
}
