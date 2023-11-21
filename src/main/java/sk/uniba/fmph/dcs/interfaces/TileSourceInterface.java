package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TileSourceInterface extends TileStateInterface {
  List<Tile> take(int index);
  boolean isEmpty();
  void startNewRound();
}
