# Iteration 1
Looking at the code in the first glance, these are the major problems
> `All the code sits inside a main method`

_A simplest solution would be to move all the code to a public method_

> `Random choices`

_The program uses `(int)(Math.random()*3);` to simulate the choices of each player. This does not allow us to write any tests. 
Let us control this using DI. 
We have added a `ChoiceGenerator` interface with just 1 implementation `RandomChoiceGenerator` which is exactly the same as `Player.playerChoice`. This now
allows us to add a `FixedChoice` implementation for our test_

> The program writes everything to `stdout`.

_Instead of touching the core logic, start returning the `stdout`. So the `void` method will now return `List<String>` which can be easily asserted_

> The Game declares the first player winning 3 games as the winner. This constant value `3` is buried inside the codebase. 

_Inject the `GAME_WON` deciding factor. This will enable us to write tests in a more controlled fashion. Also we can default it to `3`_

> Values for Rock, Paper and Scissors are not constants.

_Move them to an enum_

---

# Iteration 2
Let us now start to add tests assuming the program works. A simple way to write assertions here is actual = expected. This is far from correct, but it's a starting point.

>We will use `JUnit5` and `AssertJ` matchers for our test setup

As we now have a way to inject `FixedChoiceGenerator` and `GameWonCriteria`, out first test is with Player1 playing `Rock` and Player2 playing `Paper`. As we know `paper covers rock`, so Player2 wins

We create a stub for the ChoiceGenerator and complete our first awesome test. While running it, this happens
```
***** Round: 0 *********************

Number of Draws: 0

Player 1: rock	 Player 1 Total Wins: 0
Player 2: paper	 Player 2 Total Wins: 0
Player 2 Wins

***** Round: 1 *********************

Number of Draws: 0


java.lang.ArrayIndexOutOfBoundsException: Index 2 out of bounds for length 2
```
Now we wanted the program to stop after 1 iteration, and our choices seemed to have been stubbed properly. Then what has gone wrong ?
Just look at the following lines of code. We are assigning p1.wins to a variable, incrementing it and then using getWins getter to fetch its value. The player class does have a setter to update win count. There is also an amazing comment which points us to the same direction

```
  int p1Wins = p1.wins;
  ..
  ..
     p2Wins++;  // trying a couple different ways to get count to work
  ..
  ..
   if ((p1.getWins() >= gamesToWin) || (p2.getWins() >= gamesToWin)) {
        gameWon = true;
```

Changing `p2Wins++` to `p2.setWins()`. Just like that, we fixed a small bug and have a test to assert it... \m/

Looking at the test output, a couple of things still seem to be off
- The Round starts with zero
- Player 2 Total wins is still 0. 
Going back to the code we understand that this summary is printed before the actual evaluation of choices. This is another bug that needs attention. But as we do not have enough test coverage, we will come back to this later.