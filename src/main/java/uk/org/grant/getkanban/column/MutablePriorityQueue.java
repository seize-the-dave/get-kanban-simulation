package uk.org.grant.getkanban.column;

import com.google.common.collect.ForwardingQueue;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class MutablePriorityQueue<T> extends ForwardingQueue<T> {
    private PriorityQueue<T> queue;
    private Comparator<T> comparator;

    public MutablePriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
        queue = new PriorityQueue<>(comparator);
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        List<T> items = queue.stream().collect(Collectors.toList());
        queue = new PriorityQueue<>(comparator);
        queue.addAll(items);
    }

    public Comparator<T> getComparator() {
        return this.comparator;
    }

    @Override
    protected Queue<T> delegate() {
        return queue;
    }
}
