package uk.org.grant.getkanban.column;

public interface Workable<T> {
    void doTheWork(T t);
}
