package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PutTilesInterface;
import sk.uniba.fmph.dcs.interfaces.TileStateInterface;
import sk.uniba.fmph.dcs.interfaces.TakeAllTilesInterface;
import sk.uniba.fmph.dcs.interfaces.UsedTilesInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class UsedTiles implements UsedTilesInterface {
  private List<Tile> tiles;

  public UsedTiles() {

    tiles = new ArrayList<>();
  }

  @Override
  public Collection<Tile> takeAll() {

    List<Tile> tiles = new ArrayList<>(this.tiles);

    this.tiles.clear();

    return tiles;
  }

  @Override
  public void put(Collection<Tile> tiles) {

    this.tiles.addAll(tiles);
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
