package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Board;
import sk.uniba.fmph.dcs.FinishRoundResult;
import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface PlayerInterface {
  String getName();
  Board getBoard();
  void put(List<Tile> tiles, int destinationIndex);
  FinishRoundResult finishRound();
  void endGame();
}
