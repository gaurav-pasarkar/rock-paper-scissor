public class RandomChoiceGenerator implements ChoiceGenerator {

  @Override
  /**
   * Randomly choose rock, paper, or scissors
   */
  public Choices getChoice() {
    Choices choice = null;
    int c = (int) (Math.random() * 3);
    switch (c) {
      case 0:
        choice = Choices.ROCK;
        break;
      case 1:
        choice = Choices.PAPER;
        break;
      case 2:
        choice = Choices.SCISSOR;
        break;
    }
    return choice;
  }
}
