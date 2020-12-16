import java.util.ArrayList;
import java.util.List;

public class Game {

  private final List<String> outputs = new ArrayList<>();
  private final int gamesToWin;

  private final Player p1;
  private final Player p2;
  private int roundsPlayed = 1;    // Number of rounds played
  private int numberOfDraws = 0;

  public Game(ChoiceGenerator choiceGenerator) {
    this.gamesToWin = 3;
    this.p1 = new Player(choiceGenerator);
    this.p2 = new Player(choiceGenerator);
  }

  public Game(ChoiceGenerator choiceGenerator, int gamesToWin) {
    this.gamesToWin = gamesToWin;
    this.p1 = new Player(choiceGenerator);
    this.p2 = new Player(choiceGenerator);
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
    captureOutputAndPrint("Player 1: " + p1.getChoice().getValue() +
        "\t Player 1 Total Wins: " + p1.getWins());
    captureOutputAndPrint("Player 2: " + p2.getChoice().getValue() +
        "\t Player 2 Total Wins: " + p2.getWins());
    captureOutputAndPrint(roundResult);
  }

  public List<String> execute() {
    boolean gameWon = false;

    // Game Loop
    do {
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