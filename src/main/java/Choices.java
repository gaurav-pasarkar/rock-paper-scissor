public enum Choices {
  ROCK("rock"){
    @Override
    public boolean canBeat(Choices choice) {
      return choice.equals(SCISSOR);
    }
  },
  PAPER("paper") {
    @Override
    public boolean canBeat(Choices choice) {
      return choice.equals(ROCK);
    }
  },
  SCISSOR("scissors") {
    @Override
    public boolean canBeat(Choices choice) {
      return choice.equals(PAPER);
    }
  };

  private final String value;

  Choices(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public abstract boolean canBeat(Choices choice);
}
