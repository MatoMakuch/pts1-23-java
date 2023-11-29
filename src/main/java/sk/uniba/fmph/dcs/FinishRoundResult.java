package sk.uniba.fmph.dcs;

public enum FinishRoundResult {
  NORMAL, GAME_FINISHED;

  @Override
  public String toString() {

    return switch (this) {
      case NORMAL -> "normal";
      case GAME_FINISHED -> "game_finished";
    };
  }
}