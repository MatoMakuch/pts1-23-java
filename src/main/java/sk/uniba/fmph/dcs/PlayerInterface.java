package sk.uniba.fmph.dcs;

import java.util.List;

public interface PlayerInterface {
  String getName();
  Board getBoard();
  void put(List<Tile> tiles, int destinationIndex);
  FinishRoundResult finishRound();
  void endGame();
}
