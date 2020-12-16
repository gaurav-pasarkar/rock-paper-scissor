import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class PlayerTest {

  @Test
  public void givenP1ChoiceIsBetterThatP2Choice_P1canBeatP2() {
    Player p1 = new Player(getFixedChoiceGenerator(Choices.ROCK));
    Player p2 = new Player(getFixedChoiceGenerator(Choices.SCISSOR));

    p1.makeChoice();
    p2.makeChoice();

    assertThat(p1.canBeat(p2)).isTrue();
    assertThat(p2.canBeat(p1)).isFalse();
  }

  @Test
  public void givenP2ChoiceIsBetterThatP1Choice_P2canBeatP1() {
    Player p1 = new Player(getFixedChoiceGenerator(Choices.ROCK));
    Player p2 = new Player(getFixedChoiceGenerator(Choices.PAPER));

    p1.makeChoice();
    p2.makeChoice();

    assertThat(p2.canBeat(p1)).isTrue();
    assertThat(p1.canBeat(p2)).isFalse();
  }

  @Test
  public void givenP2ChoiceIsEqualToP1Choice_noCanWin() {
    Player p1 = new Player(getFixedChoiceGenerator(Choices.ROCK));
    Player p2 = new Player(getFixedChoiceGenerator(Choices.ROCK));

    p1.makeChoice();
    p2.makeChoice();

    assertThat(p2.canBeat(p1)).isFalse();
    assertThat(p1.canBeat(p2)).isFalse();
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