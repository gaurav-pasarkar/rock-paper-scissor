import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  public void shouldDeclarePlayer2AsWinner_As_PaperCoversRock() {
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
  public void shouldDeclarePlayer1AsWinner_As_PaperCoversRock() {
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
  public void shouldDeclarePlayer1AsWinner_As_RockCrushesScissors() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.ROCK, Choices.SCISSOR);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: rock");
    assertThat(outputs.get(3)).startsWith("Player 2: scissors");
    assertThat(outputs.get(4)).isEqualTo("Player 1 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldDeclarePlayer2AsWinner_As_RockCrushesScissors() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.SCISSOR, Choices.ROCK);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: scissors");
    assertThat(outputs.get(3)).startsWith("Player 2: rock");
    assertThat(outputs.get(4)).isEqualTo("Player 2 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldDeclarePlayer1AsWinner_As_ScissorsCutsPaper() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.SCISSOR, Choices.PAPER);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: scissors");
    assertThat(outputs.get(3)).startsWith("Player 2: paper");
    assertThat(outputs.get(4)).isEqualTo("Player 1 Wins");
    assertThat(outputs.get(5)).isEqualTo("GAME WON");
    assertThat(outputs.get(6)).isEqualTo("");
  }

  @Test
  public void shouldDeclarePlayer2AsWinner_As_ScissorsCutsPaper() {
    ChoiceGenerator fixedChoice = getFixedChoiceGenerator(Choices.PAPER, Choices.SCISSOR);

    List<String> outputs = new Game(fixedChoice, 1).execute();

    assertThat(outputs.size()).isEqualTo(7);
    assertThat(outputs.get(0)).startsWith("***** Round: ");
    assertThat(outputs.get(1)).startsWith("Number of Draws: ");
    assertThat(outputs.get(2)).startsWith("Player 1: paper");
    assertThat(outputs.get(3)).startsWith("Player 2: scissors");
    assertThat(outputs.get(4)).isEqualTo("Player 2 Wins");
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

  private ChoiceGenerator getFixedChoiceGenerator(Choices ...allChoices) {
    return new ChoiceGenerator() {
      final List<String> choices = Arrays.stream(allChoices).map(Choices::getValue).collect(Collectors.toList());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };
  }

}