package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PatternLineInterface;
import sk.uniba.fmph.dcs.interfaces.TileStateInterface;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLine implements PatternLineInterface {

  private final WallLineInterface wallLine;
  private final Floor floor;
  private int capacity;
  private final List<Tile> tiles;
  private Tile type;

  public PatternLine(WallLineInterface wallLine, Floor floor, int capacity) {

    this.wallLine = wallLine;
    this.floor = floor;
    this.capacity = capacity;

    this.tiles = new ArrayList<>();
  }

  @Override
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

  @Override
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
  public String getState() {

    final List<Tile> tiles = new ArrayList<>();

    for (int i = 0; i < capacity; i++) {

      if (i < this.tiles.size()) {

        tiles.add(this.tiles.get(i));
      }
      else {

        tiles.add(null);
      }
    }

    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) {

    final List<Tile> tiles = Tile.fromString(state);

    this.capacity = tiles.size();

    this.tiles.clear();
    for (var tile : tiles) {

      if (tile == null) {

        break;
      }

      this.tiles.add(tile);
    }

    this.type = null;
    if (this.tiles.size() > 0) {

      this.type = this.tiles.get(0);
    }
  }

  @Override
  public String toString() {

    return getState();
  }
}