package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class TableCenter implements TileSource {
  private List<Tile> tiles;
  private boolean isFirstPlayer;

  public TableCenter() {
    tiles = new ArrayList<>();
    isFirstPlayer = true;
    tiles.add(Tile.STARTING_PLAYER); // Adding the starting player tile initially
  }

  public void add(List<Tile> tilesToAdd) {
    this.tiles.addAll(tilesToAdd);
  }

  @Override
  public List<Tile> take(int index) {

    if (index < 0 || index >= tiles.size()) {

      throw new IllegalArgumentException("Index out of bounds.");
    }

    Tile pickedTile = tiles.get(index);

    List<Tile> pickedTiles = new ArrayList<>();

    // Use an iterator to avoid ConcurrentModificationException
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
    tiles.add(Tile.STARTING_PLAYER);

    isFirstPlayer = true;
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
