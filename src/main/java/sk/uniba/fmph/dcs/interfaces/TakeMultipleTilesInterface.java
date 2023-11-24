package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TakeMultipleTilesInterface {
  List<Tile> take(int count);
}
