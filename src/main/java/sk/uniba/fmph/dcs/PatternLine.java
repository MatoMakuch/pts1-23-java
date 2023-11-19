package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLine {

  private WallLine wallLine;
  private Floor floor;
  private int capacity;
  private List<Tile> tiles;
  private Tile type;

  public PatternLine(WallLine wallLine, Floor floor, int capacity) {

    this.wallLine = wallLine;
    this.floor = floor;
    this.capacity = capacity;

    this.tiles = new ArrayList<>();
  }

  public static class PatternLineState {
    private String tiles;
    private Character type;
    private int capacity;

    private PatternLineState(List<Tile> tiles, Tile type, int capacity) {

      this.tiles = Tile.toString(tiles);
      this.type = Tile.toChar(type);

      this.capacity = capacity;
    }
  }

  public PatternLineState saveState() {

    return new PatternLineState(tiles, type, capacity);
  }

  public void restoreState(PatternLineState state) {

    this.tiles = Tile.fromString(state.tiles);
    this.type = Tile.fromChar(state.type);
    this.capacity = state.capacity;
  }

  public int getCapacity() {

    return capacity;
  }

  public List<Tile> getTiles() {

    return Collections.unmodifiableList(tiles);
  }

  public boolean put(List<Tile> tilesToAdd) {

    if (tilesToAdd.size() == 0) {

      throw new IllegalArgumentException("No tiles to add.");
    }

    if (type != null && type != tilesToAdd.get(0)) {

      throw new IllegalArgumentException("Invalid tile type.");
    }

    if (!wallLine.canPutTile(tilesToAdd.get(0))) {

      throw new IllegalStateException("Cannot put tile on the wall.");
    }

    type = tilesToAdd.get(0);

    List<Tile> tilesFalling = new ArrayList<>();

    for (Tile tile : tilesToAdd) {

      if (tiles.size() < capacity) {

        tiles.add(tile);

      }
      else {

        tilesFalling.add(tile);
      }
    }

    floor.put(tilesFalling);

    return true;
  }

  public Points finishRound() {

    if (tiles.size() != capacity) {

      return new Points(0);
    }

    if (!wallLine.canPutTile(type)) {

      throw new IllegalStateException("Cannot put tile on the wall.");
    }

    var points = wallLine.putTile(type);

    UsedTiles.getInstance().give(tiles);
    tiles.clear();
    type = null;

    return points;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < capacity; i++) {

      if (i < tiles.size()) {

        builder.append(Tile.toChar(tiles.get(i)));
      }
      else {

        builder.append('.');
      }
    }

    return builder.toString();
  }
}