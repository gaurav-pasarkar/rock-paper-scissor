public class RandomChoiceGenerator implements ChoiceGenerator {

  @Override
  /**
   * Randomly choose rock, paper, or scissors
   */
  public String getChoice() {
    String choice = "";
    int c = (int) (Math.random() * 3);
    switch (c) {
      case 0:
        choice = Choices.ROCK.getValue();
        break;
      case 1:
        choice = Choices.PAPER.getValue();
        break;
      case 2:
        choice = Choices.SCISSOR.getValue();
        break;
    }
    return choice;
  }
}
