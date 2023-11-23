package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

public interface PutTileInterface {
  boolean canPutTile(Tile tile);
  Points putTile(Tile tile);
}
