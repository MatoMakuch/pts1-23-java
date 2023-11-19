package sk.uniba.fmph.dcs;

import java.util.Arrays;

public class WallLine {

  private final Tile[] tileTypes;
  private final boolean[] filledPositions;
  private WallLine lineUp;
  private WallLine lineDown;

  public WallLine(Tile[] tileTypes) {

    this.tileTypes = tileTypes;

    this.filledPositions = new boolean[tileTypes.length];
  }

  public void setLineUp(WallLine lineUp) {

    this.lineUp = lineUp;
  }

  public void setLineDown(WallLine lineDown) {

    this.lineDown = lineDown;
  }

  public boolean canPutTile(Tile tile) {

    int index = Arrays.asList(tileTypes).indexOf(tile);

    return index >= 0 && !filledPositions[index];
  }

  public Tile[] getTiles() {

    var tiles = new Tile[tileTypes.length];

    for (int i = 0; i < tileTypes.length; i++) {

      if (filledPositions[i]) {

        tiles[i] = tileTypes[i];
      }
      else {

        tiles[i] = null;
      }
    }

    return tiles;
  }

  public Points putTile(Tile tile) {
    int index = Arrays.asList(tileTypes).indexOf(tile);

    if (index < 0 || filledPositions[index]) {
      throw new IllegalStateException("Tile cannot be placed here.");
    }

    filledPositions[index] = true;

    int points = 1; // Start with 1 point for the tile itself

    // Check horizontally
    int horizontalCount = 1; // Include the placed tile.

    // Check to the left.
    for (int i = index - 1; i >= 0; i--) {

      if (!filledPositions[i]) {

        break;
      }

      horizontalCount++;
    }

    // Check to the right.
    for (int i = index + 1; i < filledPositions.length; i++) {

      if (!filledPositions[i]) {

        break;
      }

      horizontalCount++;
    }

    if (horizontalCount > 1) {

      points += (horizontalCount - 1);
    }

    // Check vertically

    int verticalCount = 1; // Include the placed tile.

    if (lineUp != null && lineUp.getTiles()[index] != null) {

      verticalCount++;
    }
    if (lineDown != null && lineDown.getTiles()[index] != null) {

      verticalCount++;
    }

    if (verticalCount > 1) {

      points += (verticalCount - 1);
    }

    return new Points(points);
  }

  public String state() {

    StringBuilder state = new StringBuilder();

    for (int i = 0; i < tileTypes.length; i++) {

      state.append(filledPositions[i] ? tileTypes[i].toString() : ".");
    }

    return state.toString();
  }
}
