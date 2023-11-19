package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class PatternLine {

  private WallLine wallLine;
  private Floor floor;
  private final int capacity;
  private final List<Tile> tiles;
  private Tile type;

  public PatternLine(WallLine wallLine, Floor floor, int capacity) {

    this.wallLine = wallLine;
    this.floor = floor;
    this.capacity = capacity;

    this.tiles = new ArrayList<>();
  }

  public boolean put(List<Tile> tilesToAdd) {

    if (tilesToAdd.size() == 0) {

      throw new IllegalArgumentException("No tiles to add.");
    }

    if (type != null && type != tilesToAdd.get(0)) {

      throw new IllegalArgumentException("Invalid tile type.");
    }

    if (wallLine.canPutTile(tilesToAdd.get(0))) {

      type = tilesToAdd.get(0);
    }

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

    tiles.clear();
    type = null;

    return points;
  }

  public String state() {

    StringBuilder builder = new StringBuilder();

    for (Tile tile : tiles) {

      builder.append(tile.toString());
    }

    return builder.toString();
  }
}