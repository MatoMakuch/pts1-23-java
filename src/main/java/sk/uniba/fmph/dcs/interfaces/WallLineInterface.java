package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

public interface WallLineInterface extends TileStateInterface {

  WallLineInterface getLineUp();
  void setLineUp(WallLineInterface lineUp);
  WallLineInterface getLineDown();
  void setLineDown(WallLineInterface lineDown);
  boolean canPutTile(Tile tile);
  Points putTile(Tile tile);
}

