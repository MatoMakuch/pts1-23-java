package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  //#region State

  public static class WallLineState {

    private String tiles;

    private WallLineState(List<Tile> tiles) {

      this.tiles = Tile.toString(tiles);
    }
  }

  public WallLineState saveState() {

    final List<Tile> tiles = new ArrayList<>();
    for (int i = 0; i < tileTypes.length; i++) {

      if (filledPositions[i]) {

        tiles.add(tileTypes[i]);
      }
    }

    return new WallLineState(tiles);
  }

  public void restoreState(WallLineState state) {

    final List<Tile> tiles = Tile.fromString(state.tiles);
    for (int i = 0; i < tileTypes.length; i++) {

      if (tiles.contains(tileTypes[i])) {

        filledPositions[i] = true;
      }
    }
  }

  //#endregion

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

    int points = 0;

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

      points += (horizontalCount);
    }

    // Check vertically

    int verticalCount = 1; // Include the placed tile.

    // Check upwards
    WallLine currentLine = this.lineUp;
    while (currentLine != null && currentLine.getTiles()[index] != null) {

      currentLine = currentLine.lineUp;
      verticalCount++;
    }

    // Check downwards
    currentLine = this.lineDown;
    while (currentLine != null && currentLine.getTiles()[index] != null) {

      currentLine = currentLine.lineDown;
      verticalCount++;
    }

    if (verticalCount > 1) {
      points += verticalCount;
    }

    // Minimum of 1 point.
    if (points == 0) {

      points = 1;
    }

    return new Points(points);
  }

  public String state() {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < tileTypes.length; i++) {

      // Add tile if it exists, otherwise add dot.
      if (filledPositions[i]) {

        builder.append(tileTypes[i]);
      }
      else {

        builder.append(".");
      }
    }

    builder.append("\n");

    return builder.toString();
  }

  @Override
  public String toString() {

    final List<Tile> tiles = new ArrayList<>();
    for (int i = 0; i < tileTypes.length; i++) {

      if (filledPositions[i]) {

        tiles.add(tileTypes[i]);
      }
      else
      {

        tiles.add(null);
      }
    }

    return Tile.toString(tiles);
  }
}
