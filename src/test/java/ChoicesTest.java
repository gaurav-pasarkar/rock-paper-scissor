import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChoicesTest {

  @Test
  public void givenRock_shouldCrushScissor() {
    assertThat(Choices.ROCK.canBeat(Choices.SCISSOR)).isTrue();
    assertThat(Choices.ROCK.canBeat(Choices.PAPER)).isFalse();
    assertThat(Choices.ROCK.canBeat(Choices.ROCK)).isFalse();
  }

  @Test
  public void givenPaper_shouldCoverRock() {
    assertThat(Choices.PAPER.canBeat(Choices.ROCK)).isTrue();
    assertThat(Choices.PAPER.canBeat(Choices.SCISSOR)).isFalse();
    assertThat(Choices.PAPER.canBeat(Choices.PAPER)).isFalse();
  }

  @Test
  public void givenScissors_shouldCutPaper() {
    assertThat(Choices.SCISSOR.canBeat(Choices.PAPER)).isTrue();
    assertThat(Choices.SCISSOR.canBeat(Choices.ROCK)).isFalse();
    assertThat(Choices.SCISSOR.canBeat(Choices.SCISSOR)).isFalse();
  }

}