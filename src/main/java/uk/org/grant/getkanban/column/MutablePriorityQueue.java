package uk.org.grant.getkanban.column;

import com.google.common.collect.ForwardingQueue;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class MutablePriorityQueue<T> extends ForwardingQueue<T> {
    private PriorityQueue<T> queue;

    public MutablePriorityQueue(Comparator<T> comparator) {
        queue = new PriorityQueue<>(comparator);
    }

    public void setComparator(Comparator<T> comparator) {
        List<T> items = queue.stream().collect(Collectors.toList());
        queue = new PriorityQueue<>(comparator);
        queue.addAll(items);
    }

    @Override
    protected Queue<T> delegate() {
        return queue;
    }
}
