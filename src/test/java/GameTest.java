import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  public void shouldDeclarePlayer2AsWinner_As_PaperCoversRock() {
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.ROCK.getValue(), Choices.PAPER.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.PAPER.getValue(), Choices.ROCK.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.ROCK.getValue(), Choices.SCISSOR.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.SCISSOR.getValue(), Choices.ROCK.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.SCISSOR.getValue(), Choices.PAPER.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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
    ChoiceGenerator fixedChoice = new ChoiceGenerator() {
      final List<String> choices = asList(Choices.PAPER.getValue(), Choices.SCISSOR.getValue());
      private int index = 0;

      @Override
      public String getChoice() {
        return choices.get(index++);
      }
    };

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

}