package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import sk.uniba.fmph.dcs.interfaces.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BoardIntegrationTest {

  // Define the point pattern for the floor line.
  final static List<Points> pointPattern = List.of(
      new Points(-1),
      new Points(-1),
      new Points(-2),
      new Points(-2),
      new Points(-3),
      new Points(-3)
  );
  private Board board;
  private FloorInterface floor;
  private List<PatternLineInterface> patternLines;
  private List<WallLineInterface> wallLines;

  @Before
  public void setUp() {

    //#region Used Tiles

    UsedTilesInterface usedTiles = new UsedTiles();

    //#endregion

    //#region Floor

    floor = new Floor(usedTiles, pointPattern);

    //#endregion

    wallLines = new ArrayList<>();
    patternLines = new ArrayList<>();

    TilePermutationIterator iterator = new TilePermutationIterator();

    int i = 0; // Initialize the counter.
    while (iterator.hasNext()) {

      //#region WallLine

      final WallLineInterface newWallLine = new WallLine(iterator.next());

      if (i > 0) { // For all but the first WallLine.

        newWallLine.setLineUp(wallLines.get(i - 1));
        wallLines.get(i - 1).setLineDown(newWallLine);
      }

      wallLines.add(newWallLine);

      //#endregion

      //#region PatternLine

      final PatternLineInterface patternLine = new PatternLine(usedTiles, floor, newWallLine, i + 1);

      patternLines.add(patternLine);

      //#endregion

      i++; // Increment the counter.
    }

    //#region Board

    board = new Board(floor, new ArrayList<>(patternLines), new ArrayList<>(wallLines));

    //#endregion
  }

  @Test
  public void testAddTilesToPatternLine() {

    // Assuming that the pattern line can accept a certain color and number of tiles.
    Tile color = Tile.BLUE;
    int lineIndex = 1; // Choosing the second pattern line (index 1).
    int numberOfTiles = 2; // Line can hold 2 tiles.

    List<Tile> tiles = new ArrayList<>(Collections.nCopies(numberOfTiles, color));
    board.put(tiles, lineIndex);

    // Verify that the tiles are added to the specified pattern line.
    assertEquals(numberOfTiles, Tile.fromString(patternLines.get(lineIndex).getState()).size());
    for (Tile tile : Tile.fromString(patternLines.get(lineIndex).getState())) {

      assertEquals(color, tile);
    }

    // Verify that no tiles are added to the floor line.
    assertTrue(Tile.fromString(floor.getState()).isEmpty());
  }

  @Test
  public void testPatternLineColorRestriction() {

    // Add some tiles to a pattern line.
    Tile initialColor = Tile.BLUE;
    int lineIndex = 1;
    board.put(Collections.singletonList(initialColor), lineIndex);

    // Attempt to add different colored tiles to the same line.
    Tile differentColor = Tile.RED;

    // Verify that adding a different color to the pattern line throws an exception
    assertThrows(IllegalArgumentException.class, () -> board.put(Collections.singletonList(differentColor), lineIndex));
  }

  @Test
  public void testMoveTileFromPatternLineToWall() {

    // Fill a pattern line completely.
    Tile color = Tile.BLUE;
    int lineIndex = 1;
    for (int i = 0; i <= lineIndex; i++) {

      board.put(Collections.singletonList(color), lineIndex);
    }

    board.finishRound();

    // Verify that the rightmost tile moves to the wall.
    assertTrue(Tile.fromString(wallLines.get(lineIndex).getState()).contains(color));

    // Verify that the remaining tiles are cleared from the pattern line.
    assertTrue(Tile.fromString(patternLines.get(lineIndex).getState()).stream().allMatch(Objects::isNull));
  }

  @Test
  public void testLosingPointsDueToFloorLine() {

    // Add tiles to the floor line.
    Tile color = Tile.BLUE;
    int numberOfTiles = 3;
    List<Tile> tiles = new ArrayList<>(Collections.nCopies(numberOfTiles, color));

    board.put(tiles, -1); // -1 indicates adding to the floor line.

    board.finishRound();

    // Verify that points are deducted according to the floor line's point pattern.
    int expectedPointsDeduction = pointPattern.subList(0, numberOfTiles).stream().mapToInt(Points::getValue).sum();
    int actualPoints = board.getPoints().getValue();

    assertTrue(actualPoints <= expectedPointsDeduction);
  }
}