package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.FinishRoundResult;
import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface BoardInterface {
  Points getPoints();
  void put(List<Tile> tiles, int destinationIndex);
  FinishRoundResult finishRound();
  void endGame();

}
