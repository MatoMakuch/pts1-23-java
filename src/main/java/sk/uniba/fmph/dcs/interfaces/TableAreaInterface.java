package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TableAreaInterface {
  List<Tile> take(int sourceIndex, int tileIndex);
  boolean isRoundEnd();
  void startNewRound();
}
