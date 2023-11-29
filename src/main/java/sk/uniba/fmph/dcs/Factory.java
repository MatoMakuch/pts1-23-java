package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PutTilesInterface;
import sk.uniba.fmph.dcs.interfaces.TakeMultipleTilesInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Factory implements TileSourceInterface {

  private static final int MAX_TILES = 4;
  private final TakeMultipleTilesInterface bag;
  private final PutTilesInterface tableCenter;
  private List<Tile> tiles = new ArrayList<>();

  public Factory(final TakeMultipleTilesInterface bag, final PutTilesInterface tableCenter) {

    this.bag = bag;
    this.tableCenter = tableCenter;
  }

  private void fillFactory() {

    tiles.clear();
    tiles = bag.take(MAX_TILES);
  }

  @Override
  public List<Tile> take(final int index) throws IllegalArgumentException {

    if (index < 0 || index >= tiles.size()) {

      throw new IllegalArgumentException("Index out of bounds");
    }

    final Tile selectedColor = tiles.get(index);

    final List<Tile> pickedTiles = new ArrayList<>();
    final List<Tile> remainingTiles = new ArrayList<>();

    // Use an iterator to avoid ConcurrentModificationException while removing.
    final Iterator<Tile> iterator = tiles.iterator();

    while (iterator.hasNext()) {

      Tile tile = iterator.next();

      if (tile == selectedColor) {

        pickedTiles.add(tile);
        iterator.remove();
      } else {

        remainingTiles.add(tile);
        iterator.remove();
      }
    }

    tableCenter.put(remainingTiles);

    return pickedTiles;
  }

  @Override
  public boolean isEmpty() {

    return tiles.isEmpty();
  }

  @Override
  public void startNewRound() {

    fillFactory();
  }

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) {
    tiles = Tile.fromString(state);
  }
}
