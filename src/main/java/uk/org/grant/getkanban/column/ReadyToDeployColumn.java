package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadyToDeployColumn extends AbstractColumn {
    private final Queue<Card> todo = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private final Queue<Card> done = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
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
    public void visit(Context context) {
        // TODO: Automate regression for I2
//        System.out.println("In " + this + " on " + context.getDay());
        while (true) {
//            System.out.println("Pull from " + upstream);
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                addCard(optionalCard.get());
//                System.out.println("Pulled " + optionalCard.get() + " into " + this);
            } else {
//                System.out.println(upstream + " has nothing to pull.");
                break;
            }
        }
        if (context.getDay().getOrdinal() % 3 == 0) {
            done.addAll(todo);
            todo.clear();;
        }
    }

    @Override
    public String toString() {
        return "[READY TO DEPLOY (" + todo.size() + "/" + done.size() + "/-)]";
    }
}
