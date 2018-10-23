package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public class DeployedColumn implements Column {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    private final Column upstream;

    public DeployedColumn(Column upstream) {
        this.upstream = upstream;
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public void allocateDice(ActivityDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        return null;
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
            card.setDayDeployed(day.getOrdinal());
            addCard(card);
        }
    }
}
