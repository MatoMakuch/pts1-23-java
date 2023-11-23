package sk.uniba.fmph.dcs.interfaces;

public interface WallLineInterface extends PutTileInterface, TileStateInterface {

  WallLineInterface getLineUp();
  void setLineUp(WallLineInterface lineUp);
  WallLineInterface getLineDown();
  void setLineDown(WallLineInterface lineDown);
}

