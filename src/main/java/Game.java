import java.util.ArrayList;
import java.util.List;

public class Game {

  private final List<String> outputs = new ArrayList<>();
  private final ChoiceGenerator choiceGenerator;
  private final int gamesToWin;

  private final Player p1 = new Player();
  private final Player p2 = new Player();
  private int roundsPlayed = 1;    // Number of rounds played
  private int numberOfDraws = 0;
  private String p1Choice;
  private String p2Choice;

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

  public List<String> execute() {
    boolean gameWon = false;

    // Game Loop
    do {
      p1Choice = choiceGenerator.getChoice();
      p2Choice = choiceGenerator.getChoice();

      if ((p1Choice.equals(Choices.ROCK.getValue())) && (p2Choice.equals(Choices.PAPER.getValue()))) {
        p2.declareWinner();  // trying a couple different ways to get count to work
        computeRoundSummary("Player 2 Wins");
      } else if ((p1Choice.equals(Choices.PAPER.getValue())) && (p2Choice.equals(Choices.ROCK.getValue()))) {
        p1.declareWinner();
        computeRoundSummary("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.ROCK.getValue())) && (p2Choice.equals(Choices.SCISSOR.getValue()))) {
        p1.declareWinner();
        computeRoundSummary("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.SCISSOR.getValue())) && (p2Choice.equals(Choices.ROCK.getValue()))) {
        p2.declareWinner();
        computeRoundSummary("Player 2 Wins");
      } else if ((p1Choice.equals(Choices.SCISSOR.getValue())) && (p2Choice.equals(Choices.PAPER.getValue()))) {
        p1.declareWinner();
        computeRoundSummary("Player 1 Wins");
      } else if ((p1Choice.equals(Choices.PAPER.getValue())) && (p2Choice.equals(Choices.SCISSOR.getValue()))) {
        p2.declareWinner();
        computeRoundSummary("Player 2 Wins");
      }
      if (p1Choice == p2Choice) {
        numberOfDraws++;
        computeRoundSummary("\n\t\t\t Draw \n");
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

  public void declareWinner() {
    wins++;
  }

  public int getWins() {
    return (wins);
  }
}