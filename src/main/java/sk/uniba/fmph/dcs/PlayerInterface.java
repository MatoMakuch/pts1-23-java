package sk.uniba.fmph.dcs;

import java.util.List;

public interface PlayerInterface {
  String getName();
  void put(List<Tile> tiles, int destinationIndex);
  FinishRoundResult finishRound();
  void endGame();
}
