package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Floor {
  private final UsedTilesInterface usedTiles;
  private final List<Points> pointPattern;
  private List<Tile> tiles;

  public Floor(final UsedTilesInterface usedTiles, final List<Points> pointPattern) {

    this.usedTiles = usedTiles;
    this.pointPattern = pointPattern;

    tiles = new ArrayList<>();
  }

  public void put(final Collection<Tile> tiles) {
    this.tiles.addAll(tiles);
  }

  public String state() {

    StringBuilder builder = new StringBuilder();

    for (Tile tile : tiles) {

      builder.append(tile.toString());
    }

    return builder.toString();
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

    usedTiles.give(tiles);

    tiles = new ArrayList<>();

    return new Points(sum);
  }
}
