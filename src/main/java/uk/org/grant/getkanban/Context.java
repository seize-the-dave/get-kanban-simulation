package uk.org.grant.getkanban;

public class Context {
    private final Board board;
    private final Day day;

    public Context(Board board, Day day) {
        this.board = board;
        this.day = day;
    }

    public Board getBoard() {
        return board;
    }

    public Day getDay() {
        return day;
    }
}
