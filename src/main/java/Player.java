public class Player {

  private final ChoiceGenerator choiceGenerator;
  private int wins;
  private Choices choice;

  public Player(ChoiceGenerator choiceGenerator) {
    this.choiceGenerator = choiceGenerator;
  }

  public void declareWinner() {
    wins++;
  }

  public int getWins() {
    return (wins);
  }

  public void makeChoice() {
    this.choice = choiceGenerator.getChoice();
  }

  public Choices getChoice() {
    return choice;
  }

  public boolean canBeat(Player player) {
    return this.choice.canBeat(player.getChoice());
  }
}