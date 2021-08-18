package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class FifoColumn extends AbstractColumn {
    protected final Column standard;
    protected final LinkedList<Card> cards;

    public FifoColumn(Column standard) {
        this.standard = standard;
        this.cards = new LinkedList<>();
    }

    @Override
    public void addCard(Card card, ClassOfService cos) {
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        doTheWork(context);
        return Optional.ofNullable(cards.poll());
    }
}
