package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.UsedTilesInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class UsedTiles implements UsedTilesInterface {
  private final List<Tile> tiles;

  UsedTiles() {

    tiles = new ArrayList<>();
  }

  @Override
  public Collection<Tile> takeAll() {

    final Collection<Tile> tiles = Collections.unmodifiableList(this.tiles);

    this.tiles.clear();

    return tiles;
  }

  @Override
  public void put(final Collection<Tile> tiles) {

    this.tiles.addAll(tiles);
  }

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(final String state) {

    tiles.clear();
    tiles.addAll(Tile.fromString(state));
  }

  @Override
  public String toString() {
    return getState();
  }
}
