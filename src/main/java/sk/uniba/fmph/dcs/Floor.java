package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Floor {
  private final List<Points> pointPattern;
  private List<Tile> tiles;

  public Floor() {

    // Define the point pattern for the floor line.
    this.pointPattern = new ArrayList<>();
    this.pointPattern.add(new Points(-1));
    this.pointPattern.add(new Points(-1));
    this.pointPattern.add(new Points(-2));
    this.pointPattern.add(new Points(-2));
    this.pointPattern.add(new Points(-2));
    this.pointPattern.add(new Points(-3));
    this.pointPattern.add(new Points(-3));

    this.tiles = new ArrayList<>();
  }

  public static class FloorState {
    private final String tiles;

    private FloorState(List<Tile> tiles) {
      this.tiles = Tile.toString(tiles); // Assuming Tile.toString method exists
    }
  }

  public FloorState saveState() {
    return new FloorState(tiles);
  }

  public void restoreState(FloorState state) {

    this.tiles = Tile.fromString(state.tiles); // Assuming Tile.fromString method exists
  }

  public List<Points> getPointPattern() {

    return Collections.unmodifiableList(pointPattern);
  }

  public List<Tile> getTiles() {

    return Collections.unmodifiableList(tiles);
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

    tiles = new ArrayList<>();

    return new Points(sum);
  }
}
