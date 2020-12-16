import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  public void shouldDeclarePlayer2AsWinner() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.ROCK, Choices.PAPER);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: rock");
    assertThat(outputs.get(3)).startsWith("Player 2: paper");
    assertThat(outputs.get(4)).isEqualTo("Player 2 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldDeclarePlayer1AsWinner() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.PAPER, Choices.ROCK);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: paper");
    assertThat(outputs.get(3)).startsWith("Player 2: rock");
    assertThat(outputs.get(4)).isEqualTo("Player 1 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldDeclarePlayer1AsWinner_afterFirstDraw() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.ROCK, Choices.ROCK, Choices.ROCK, Choices.SCISSOR);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(13);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: rock");
    assertThat(outputs.get(3)).startsWith("Player 2: rock");
    assertThat(outputs.get(4)).contains("Draw");
    assertThat(outputs.get(5)).isEqualTo("");

    assertThat(outputs.get(6)).startsWith("***** Round: ");
    assertThat(outputs.get(7)).startsWith("Number of Draws: ");
    assertThat(outputs.get(8)).startsWith("Player 1: rock");
    assertThat(outputs.get(9)).startsWith("Player 2: scissors");
    assertThat(outputs.get(10)).isEqualTo("Player 1 Wins");
    assertThat(outputs.get(11)).isEqualTo("GAME WON");
    assertThat(outputs.get(12)).isEqualTo("");
  }

  @Test
  public void shouldComputeRoundSummary_whenRoundIsWon() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.ROCK, Choices.PAPER);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).isEqualTo("***** Round: 1 *********************\n");
    assertThat(outputs.get(1)).isEqualTo("Number of Draws: 0\n");
    assertThat(outputs.get(2)).isEqualTo("Player 1: rock\t Player 1 Total Wins: 0");
    assertThat(outputs.get(3)).isEqualTo("Player 2: paper\t Player 2 Total Wins: 1");
    assertThat(outputs.get(4)).isEqualTo("Player 2 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldComputeRoundSummary_whenFirstRoundIsADrawAndNextWon() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.ROCK, Choices.ROCK, Choices.ROCK, Choices.PAPER);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(13);
    assertThat(outputs.get(0)).isEqualTo("***** Round: 1 *********************\n");
    assertThat(outputs.get(1)).isEqualTo("Number of Draws: 1\n");
    assertThat(outputs.get(2)).isEqualTo("Player 1: rock\t Player 1 Total Wins: 0");
    assertThat(outputs.get(3)).isEqualTo("Player 2: rock\t Player 2 Total Wins: 0");
    assertThat(outputs.get(4)).isEqualTo("\n\t\t\t Draw \n");
    assertThat(outputs.get(5)).isEqualTo("");


    assertThat(outputs.get(6)).isEqualTo("***** Round: 2 *********************\n");
    assertThat(outputs.get(7)).isEqualTo("Number of Draws: 1\n");
    assertThat(outputs.get(8)).isEqualTo("Player 1: rock\t Player 1 Total Wins: 0");
    assertThat(outputs.get(9)).isEqualTo("Player 2: paper\t Player 2 Total Wins: 1");
    assertThat(outputs.get(10)).isEqualTo("Player 2 Wins");
    assertThat(outputs.get(11)).isEqualTo("GAME WON");
    assertThat(outputs.get(12)).isEqualTo("");
  }

  private ChoiceGenerator getFixedChoiceGenerator(Choices ...allChoices) {
    return new ChoiceGenerator() {
      final List<Choices> choices = Arrays.stream(allChoices).collect(Collectors.toList());
      private int index = 0;

      @Override
      public Choices getChoice() {
        return choices.get(index++);
      }
    };
  }

}