# getKanban Simulation [![Build Status](https://travis-ci.org/seize-the-dave/get-kanban-simulation.svg?branch=master)](https://travis-ci.org/seize-the-dave/get-kanban-simulation)
This is a Java-based simulation for the [getKanban](http://getkanban.com) board game.  It provides a means by which to test various policies (prioritisation, WIP limits, etc.) without playing the game.

## Configuring

Most decisions are captured by policies.  For example:

- Which cards to expedite
- How to assign dice to cards
- The order in which to pull cards from the backlog
- Optional events (such as sending Ted on training)

The `BoardRunner` class is the starting point for changing policies.

## Building
`mvn clean package`

This will produce an executable JAR with all the dependencies included.

## Running

- `mvn exec:java`; or
- `java -jar target/get-kanban-simulation-<version>-jar-with-dependencies.jar`

Using the `java -jar` method allows you to provide the number of trials as a command-line argument, such as:

```bash
# 10,000 trials
java -jar target/get-kanban-simulation-<version>-jar-with-dependencies.jar 10000
```

Running the project shows four percentiles (`50`, `70`, `85`, `95`) of the distribution of financial summaries associated with the trials.

```
50%:

                       Day 9  Day 12  Day 15  Day 18  Day 21
New Subscribers           20      16      26      18       4
Total Subscribers         20      36      62      80      84
Cycle Revenue            200     540    1240    2000    2520
Fines or Payments          0       0   -1500       0       0
Cycle Gross Profit       200     540    -260    2000    2520
Gross Profit To Date     200     740     480    2480    5000

70%:

                       Day 9  Day 12  Day 15  Day 18  Day 21
New Subscribers           20      16      27      10      11
Total Subscribers         20      36      63      73      84
Cycle Revenue            200     540    1260    1825    2520
Fines or Payments          0       0   -1500       0       0
Cycle Gross Profit       200     540    -240    1825    2520
Gross Profit To Date     200     740     500    2325    4845

85%:

                       Day 9  Day 12  Day 15  Day 18  Day 21
New Subscribers           20      16      22       6      15
Total Subscribers         20      36      58      64      79
Cycle Revenue            200     540    1160    1600    2370
Fines or Payments          0       0   -1500       0       0
Cycle Gross Profit       200     540    -340    1600    2370
Gross Profit To Date     200     740     400    2000    4370

95%:

                       Day 9  Day 12  Day 15  Day 18  Day 21
New Subscribers           20       8      28       6      15
Total Subscribers         20      28      56      62      77
Cycle Revenue            200     420    1120    1550    2310
Fines or Payments          0       0   -1500       0       0
Cycle Gross Profit       200     420    -380    1550    2310
Gross Profit To Date     200     620     240    1790    4100
```
