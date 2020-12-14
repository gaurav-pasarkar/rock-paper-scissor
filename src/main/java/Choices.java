public enum Choices {
  ROCK("rock"),
  PAPER("paper"),
  SCISSOR("scissors");

  private final String value;

  Choices(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
