package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.*;

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
    public Collection<Card> getCards() {
        return Collections.unmodifiableCollection(cards);
    }

    @Override
    public void allocateDice(StateDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateDice> getAllocatedDice() {
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
