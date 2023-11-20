package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class UsedTiles implements UsedTilesInterface, TileStateInterface {
  private static UsedTiles instance = null;
  private List<Tile> tiles;

  private UsedTiles() {

    tiles = new ArrayList<>();
  }

  public static UsedTiles getInstance() {

    if (instance == null) {

      instance = new UsedTiles();
    }

    return instance;
  }

  @Override
  public void give(Collection<Tile> tiles) {

    tiles.addAll(tiles);
  }

  public List<Tile> getUsedTiles() {

    return new ArrayList<>(tiles);
  }

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) { tiles = Tile.fromString(state); }

  @Override
  public String toString() { return getState(); }
}
