package uk.org.grant.getkanban;

public class DayStore {
    private static ThreadLocal<Day> dayStore = new ThreadLocal<>();

    public static Day getDay() {
        return dayStore.get();
    }

    public static void setDay(Day day) {
        dayStore.set(day);
    }
}
