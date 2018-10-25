package uk.org.grant.getkanban;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadyToDeployColumn extends AbstractColumn {
    private final Queue<Card> todo = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    private final Queue<Card> done = new PriorityQueue<>(new DefaultPrioritisationStrategy());
    private final Column upstream;

    public ReadyToDeployColumn(Column upstream) {
        this.upstream = upstream;
    }


    @Override
    public void addCard(Card card) {
        todo.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return Stream.concat(this.todo.stream(), this.done.stream()).collect(Collectors.toList());
    }

    @Override
    public Optional<Card> pull() {
        return Optional.ofNullable(done.poll());
    }

    @Override
    public void visit(Day day) {
        Optional<Card> optionalCard = upstream.pull();
        if (optionalCard.isPresent()) {
            addCard(optionalCard.get());
        }
        if (day.getOrdinal() % 3 == 0) {
            done.addAll(todo);
            todo.clear();;
        }
    }
}
