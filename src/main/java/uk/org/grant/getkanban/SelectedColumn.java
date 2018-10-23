package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public class SelectedColumn implements Column {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    private Column upstream;

    public SelectedColumn(Column upstream) {
        this.upstream = upstream;
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Optional<Card> pullCard() {
        return Optional.ofNullable(cards.remove());
    }

    @Override
    public void pullFromUpstream(int day) {
        upstream.pullFromUpstream(day);
        Optional<Card> optionalCard = upstream.pullCard();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setDaySelected(day);
            addCard(card);
        }
    }

    @Override
    public void allocateDice(ActivityDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        throw new UnsupportedOperationException();
    }
}
