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
    public Optional<Card> pull() {
        return Optional.ofNullable(cards.remove());
    }

    @Override
    public void visit(Day day) {
        Optional<Card> optionalCard = upstream.pull();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setDaySelected(day.getOrdinal());
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
