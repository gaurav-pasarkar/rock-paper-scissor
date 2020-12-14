import java.util.ArrayList;
import java.util.List;

public class Game {

  private final List<String> outputs = new ArrayList<>();
  private final ChoiceGenerator choiceGenerator;
  private final int gamesToWin;

  public Game(ChoiceGenerator choiceGenerator) {
    this.choiceGenerator = choiceGenerator;
    this.gamesToWin = 3;
  }

  public Game(ChoiceGenerator choiceGenerator, int gamesToWin) {
    this.choiceGenerator = choiceGenerator;
    this.gamesToWin = gamesToWin;
  }

  private void captureOutputAndPrint(String output) {
    outputs.add(output);
    System.out.println(output);
  }
  
  public List<String> execute() {
    Player p1 = new Player();
    Player p2 = new Player();
    boolean gameWon = false;
    int roundsPlayed = 0;    // Number of rounds played
    int p1Wins = p1.wins;
    int p2Wins = p2.wins;
    int draw = 0;
    String p1Choice;
    String p2Choice;
    
    // Game Loop
    do {
      captureOutputAndPrint("***** Round: " +
          roundsPlayed + " *********************\n");
      captureOutputAndPrint("Number of Draws: " +
          draw + "\n");
      p1Choice = choiceGenerator.getChoice();
      captureOutputAndPrint("Player 1: " + p1Choice +
          "\t Player 1 Total Wins: " + p1Wins);
      p2Choice = choiceGenerator.getChoice();
      captureOutputAndPrint("Player 2: " + p2Choice +
          "\t Player 2 Total Wins: " + p2Wins);
      if ((p1Choice.equals(Choices.ROCK.getValue())) && (p2Choice.equals(Choices.PAPER.getValue()))) {
        captureOutputAndPrint("Player 2 Wins");
        p2.setWins();  // trying a couple different ways to get count to work
      } else if ((p1Choice.equals(Choices.PAPER.getValue())) && (p2Choice.equals(Choices.ROCK.getValue()))) {
        p1.setWins();
        captureOutputAndPrint("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.ROCK.getValue())) && (p2Choice.equals(Choices.SCISSOR.getValue()))) {
        p1Wins = p1.setWins();
        captureOutputAndPrint("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.SCISSOR.getValue())) && (p2Choice.equals(Choices.ROCK.getValue()))) {
        p2Wins = p2.setWins();
        captureOutputAndPrint("Player 2 Wins");
      } else if ((p1Choice.equals(Choices.SCISSOR.getValue())) && (p2Choice.equals(Choices.PAPER.getValue()))) {
        p1Wins = p1.setWins();
        captureOutputAndPrint("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.PAPER.getValue())) && (p2Choice.equals(Choices.SCISSOR.getValue()))) {
        p2Wins = p2.setWins();
        captureOutputAndPrint("Player 2 Wins");
      }
      if (p1Choice == p2Choice) {
        draw++;
        captureOutputAndPrint("\n\t\t\t Draw \n");
      }
      roundsPlayed++;
      if ((p1.getWins() >= gamesToWin) || (p2.getWins() >= gamesToWin)) {
        gameWon = true;
        captureOutputAndPrint("GAME WON");
      }
      captureOutputAndPrint("");
    } while (gameWon != true);

    return outputs;
  }
  
  public static void main(String args[]) {
    new Game(new RandomChoiceGenerator()).execute();
  }
}

/**
 *
 */
class Player {

  int wins;      // # of wins
  int winTotal;

  public int setWins() {
    int winTotal = wins++;
    return winTotal;
  }

  public int getWins() {
    return (wins);
  }
}