package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PatternLineInterface;
import sk.uniba.fmph.dcs.interfaces.PutTileInterface;
import sk.uniba.fmph.dcs.interfaces.PutTilesInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatternLine implements PatternLineInterface {
  private final PutTilesInterface usedTiles;
  private final PutTilesInterface floor;
  private final PutTileInterface wallLine;
  private final List<Tile> tiles;
  private int capacity;
  private Tile type;

  public PatternLine(final PutTilesInterface usedTiles, final PutTilesInterface floor, final PutTileInterface wallLine, final int capacity) {

    this.usedTiles = usedTiles;
    this.floor = floor;
    this.wallLine = wallLine;
    this.capacity = capacity;

    this.tiles = new ArrayList<>();
  }

  @Override
  public void put(final Collection<Tile> tiles) {

    if (tiles.size() == 0) {

      throw new IllegalArgumentException("No tiles to add.");
    }

    final Tile toPut = tiles.stream().toList().get(0);

    if (type != null && type != toPut) {

      throw new IllegalArgumentException("Invalid tile type.");
    }

    if (!wallLine.canPutTile(toPut)) {

      throw new IllegalStateException("Cannot put tile on the wall.");
    }

    type = toPut;

    final List<Tile> tilesFalling = new ArrayList<>();

    for (Tile tile : tiles) {

      if (this.tiles.size() < capacity) {

        this.tiles.add(tile);

      } else {

        tilesFalling.add(tile);
      }
    }

    floor.put(tilesFalling);
  }

  @Override
  public Points finishRound() {

    if (tiles.size() != capacity) {

      return new Points(0);
    }

    if (!wallLine.canPutTile(type)) {

      throw new IllegalStateException("Cannot put tile on the wall.");
    }

    final Points points = wallLine.putTile(type);

    usedTiles.put(tiles);
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
      } else {

        tiles.add(null);
      }
    }

    return Tile.toString(tiles);
  }

  @Override
  public void setState(final String state) {

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