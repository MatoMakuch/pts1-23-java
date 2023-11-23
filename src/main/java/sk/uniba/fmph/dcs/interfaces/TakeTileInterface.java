package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TakeTileInterface {
  boolean isEmpty();
  List<Tile> take(int index);
}
