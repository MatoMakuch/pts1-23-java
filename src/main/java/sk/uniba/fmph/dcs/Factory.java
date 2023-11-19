package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Factory implements TileSource {

  private static final int MAX_TILES = 4;
  private List<Tile> tiles = new ArrayList<>();

  private Bag bag;
  private TableCenter tableCenter;

  public Factory(Bag bag, TableCenter tableCenter) {

    this.bag = bag;
    this.tableCenter = tableCenter;

    fillFactory();
  }

  private void fillFactory() {

    tiles.clear();
    tiles = bag.getTiles(MAX_TILES);
  }

  @Override
  public List<Tile> take(int index) throws IllegalArgumentException {

    if (index < 0 || index >= tiles.size()) {

      throw new IllegalArgumentException("Index out of bounds");
    }

    Tile selectedColor = tiles.get(index);

    List<Tile> pickedTiles = new ArrayList<>();
    List<Tile> remainingTiles = new ArrayList<>();

    // Use an iterator to avoid ConcurrentModificationException while removing.
    Iterator<Tile> iterator = tiles.iterator();

    while (iterator.hasNext()) {

      Tile tile = iterator.next();

      if (tile == selectedColor) {

        pickedTiles.add(tile);
        iterator.remove();
      }
      else {

        remainingTiles.add(tile);
        iterator.remove();
      }
    }

    tableCenter.add(remainingTiles);

    return pickedTiles;
  }

  @Override
  public boolean isEmpty() {
    return tiles.isEmpty();
  }

  @Override
  public void startNewRound() {
    tiles.clear();
  }

  @Override
  public String state() {

    StringBuilder stateBuilder = new StringBuilder();
    for (Tile tile : tiles) {
      stateBuilder.append(tile.toString());
    }

    return stateBuilder.toString();
  }
}
