package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableCenter implements TableCenterInterface {

  private final List<Tile> tiles;
  private boolean isFirstPlayer;

  public TableCenter() {

    tiles = new ArrayList<>();
    isFirstPlayer = true;
  }

  @Override
  public void put(final Collection<Tile> tiles) {
    this.tiles.addAll(tiles);
  }

  @Override
  public List<Tile> take(final int index) {

    if (index < 0 || index >= tiles.size()) {

      throw new IllegalArgumentException("Index out of bounds.");
    }

    Tile pickedTile = tiles.get(index);

    List<Tile> pickedTiles = new ArrayList<>();

    // Use an iterator to avoid ConcurrentModificationException.
    tiles.removeIf(tile -> {

      boolean shouldRemove = tile == pickedTile;

      if (shouldRemove) {

        pickedTiles.add(tile);
      }

      return shouldRemove;
    });

    // Remove the starting player tile if it is the first take.
    if (isFirstPlayer && pickedTile != Tile.STARTING_PLAYER) {

      isFirstPlayer = false;

      pickedTiles.add(Tile.STARTING_PLAYER);

      tiles.remove(Tile.STARTING_PLAYER);
    }

    return pickedTiles;
  }

  @Override
  public boolean isEmpty() {

    return tiles.isEmpty();
  }

  @Override
  public void startNewRound() {

    tiles.clear();
    tiles.add(Tile.STARTING_PLAYER); // Adding the starting player tile initially.

    isFirstPlayer = true;
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
}
