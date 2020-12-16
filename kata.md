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
---

# Iteration 3
Now that we have our first test case, we can easily add tests for all the scenario's between rock, paper and scissors

The only change in the main code we need is changing `p1Win++` TO `p1.setWins();`. We already know why

> One tiny refactoring that we can do is to create a stub for our choice generator

The only remaining test to write is for the `Draw` case. As we now have a stub for fixed choice generator, we can stub following choices `Rock, Rock, Rock, Scissor`. This will ensure that the first round is a draw and Player1 wins the next.

_Again, the number of draws count seems off. It is getting printed before the evaluation of choices. Let's fix this in the next iteration_

---

# Iteration 4
Now that we have a good test coverage, let's first fix the round summary.

As discussed earlier, the core problem is that the round summary is printed before evaluation of the choices. So creating the summary after evaluation of the choices

Adding a test case to validate round summary now.
We ignored the actual value of the counts in our previous assertions.
Now that changes to something like below.
```
    assertThat(outputs.get(0)).isEqualTo("***** Round: 1 *********************\n");
    assertThat(outputs.get(1)).isEqualTo("Number of Draws: 0\n");
    assertThat(outputs.get(2)).isEqualTo("Player 1: rock\t Player 1 Total Wins: 0");
    assertThat(outputs.get(3)).isEqualTo("Player 2: paper\t Player 2 Total Wins: 1");
    assertThat(outputs.get(4)).isEqualTo("Player 2 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
```

As for our main code, we will move the method variables to instance 
```
    Player p1 = new Player();
    Player p2 = new Player();
    int roundsPlayed = 0;    // Number of rounds played
    int draw = 0;
    String p1Choice;
    String p2Choice;
```
We do not need the below variables as the number of wins is stored inside Player

~~int p1Wins = p1.wins;~~

~~int p2Wins = p2.wins;~~

Next we will create a private method to compute our summary
```
  private void computeRoundSummary(String roundResult) {
    captureOutputAndPrint("***** Round: " +
        roundsPlayed + " *********************\n");
    captureOutputAndPrint("Number of Draws: " +
        numberOfDraws + "\n");
    captureOutputAndPrint("Player 1: " + p1Choice +
        "\t Player 1 Total Wins: " + p1.getWins());
    captureOutputAndPrint("Player 2: " + p2Choice +
        "\t Player 2 Total Wins: " + p2.getWins());
    captureOutputAndPrint(roundResult);
  }
```

Finally, we will rename the `setWins`  in Player to `declareWinner`. Making the return type as void as no one is using it now.
Also getting rid of the unnecessary logic to increment a count.

---

# Iteration 5
Now that we have got all the bugs ironed out, let us see if there are any code smells

Our `GameTest` suggest that we have similar choices being evaluated for both the players.
Look at `shouldDeclarePlayer1AsWinner_As_PaperCoversRock()` and `shouldDeclarePlayer2AsWinner_As_PaperCoversRock()`

The main code also asserts this understanding

`(p1Choice.equals(Choices.PAPER.getValue())) && (p2Choice.equals(Choices.ROCK.getValue()))`

`(p1Choice.equals(Choices.ROCK.getValue())) && (p2Choice.equals(Choices.PAPER.getValue()))`

> All the `Game` cares about it is if `Player 1` canBeat `Player 2`. Whether player 1 chooses a Rock or even a Mountain, the Game need not worry.

So delegating responsibility if making a choice to the Player. Our PlayerTest now looks like this
```
    Player p1 = new Player(getFixedChoiceGenerator(Choices.ROCK));
    Player p2 = new Player(getFixedChoiceGenerator(Choices.SCISSOR));

    p1.makeChoice();
    p2.makeChoice();

    assertThat(p1.canBeat(p2)).isTrue();
    assertThat(p2.canBeat(p1)).isFalse();
```

This forces us to inject choiceGenerator to player class. Also add implementations for `makeChoice` and `canBeat`

`makeChoice` is straightForward. All it does is pick a choice from the generator

A player can only beat the other player if his choice was better than him/her. Comparing choices is not really a responsibility of the Player. This means `player.canBeat` will internally talk to `choice.canBeat` to find out if it can beat the other player
The `canBeat` in Player now looks like this
```
  public boolean canBeat(Player player) {
    return this.choice.canBeat(player.getChoice());
  }
```

Now we have the contract for Player domain model ready. The test won't pass unless we mock `choice.canBeat`. Instead of taking the mock route, lets go ahead and fulfill the contract for our Choices enum

ChoiceTest looks like this 
```
  @Test
  public void givenRock_shouldCrushScissor() {
    assertThat(Choices.ROCK.canBeat(Choices.SCISSOR)).isTrue();
    assertThat(Choices.ROCK.canBeat(Choices.PAPER)).isFalse();
    assertThat(Choices.ROCK.canBeat(Choices.ROCK)).isFalse();
  }
```
Implementing this is fairly easy. We just add an abstract method `canBeat` to our enum and implement it for every choice like this
```
 ROCK("rock"){
    @Override
    public boolean canBeat(Choices choice) {
      return choice.equals(SCISSOR);
    }
  }
```
So a Rock can only crush a Scissor. It will return false for everything else.

Adding tests for PaperCoversRock and ScissorsCutPaper is derivative now

Now that we have completed Choice implementation, we can continue our tests for Player and add tests for Player 2 winning, and a draw
The Player domain is now shaping up. All we need to do is to plug it in the Game class

The Game logic can now be simplified as below.
```
      p1.makeChoice();
      p2.makeChoice();

      if(p1.canBeat(p2)){
        p1.declareWinner();
        computeRoundSummary("Player 1 Wins");
      } else if (p2.canBeat(p1)) {
        p2.declareWinner();
        computeRoundSummary("Player 2 Wins");
      } else {
        numberOfDraws++;
        computeRoundSummary("\n\t\t\t Draw \n");
      }
```
Now the game need not maintain the choices of the players. That makes sense. Getting rid `p1Choice` and `p2Choice` to wrap up the change.

> We have our safety net of integration tests for Game available, and they work just fine. So life's good

> Looking back at our GameTest, now we do not need these duplicate tests
`shouldDeclarePlayer1AsWinner_As_PaperCoversRock()`
`shouldDeclarePlayer2AsWinner_As_PaperCoversRock()`

Let us only keep 1 for each player now