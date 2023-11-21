package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TileStateInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Floor implements TileStateInterface {
  private final List<Points> pointPattern;
  private List<Tile> tiles = new ArrayList<>();

  public Floor(List<Points> pointPattern) {

    this.pointPattern = pointPattern;
  }

  public List<Points> getPointPattern() {

    return Collections.unmodifiableList(pointPattern);
  }

  public void put(final Collection<Tile> tiles) {
    this.tiles.addAll(tiles);
  }

  public Points finishRound() {

    int sum = 0;
    for (int i = 0; i < tiles.size(); i++) {
      sum +=
          (i < pointPattern.size()
              ? pointPattern.get(i)
              : pointPattern.get(pointPattern.size() - 1))
              .getValue();
    }

    UsedTiles.getInstance().give(tiles);

    tiles.clear();

    return new Points(sum);
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
