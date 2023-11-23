package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableCenterTest {
  private TableCenter tableCenter;

  @BeforeEach
  public void setUp() {

    tableCenter = new TableCenter();

    tableCenter.startNewRound();
  }

  @Test
  public void testTakeFirstTile_IncludesStartingPlayerTile() {

    // Add some tiles to the TableCenter.
    tableCenter.put(List.of(Tile.BLUE, Tile.RED));

    // Take one of the added tiles.
    List<Tile> takenTiles = tableCenter.take(1); // Assume index 1 is for Tile.BLUE.

    assertTrue(takenTiles.contains(Tile.STARTING_PLAYER));
    assertTrue(takenTiles.contains(Tile.BLUE));
    assertEquals(2, takenTiles.size());
  }

  @Test
  public void testTakeTile_AfterFirstTake_ExcludesStartingPlayerTile() {

    // Add tiles.
    tableCenter.put(List.of(Tile.BLUE, Tile.RED));

    // First take, includes starting player tile.
    tableCenter.take(1);

    // Now index 0 is for Tile.RED.
    List<Tile> takenTiles = tableCenter.take(0);

    assertFalse(takenTiles.contains(Tile.STARTING_PLAYER));
    assertTrue(takenTiles.contains(Tile.RED));
    assertEquals(1, takenTiles.size());
  }

  @Test
  public void testTakeTile_InvalidIndex_ThrowsException() {

    assertThrows(IllegalArgumentException.class, () -> tableCenter.take(-1));
    assertThrows(IllegalArgumentException.class, () -> tableCenter.take(10)); // Assuming there are fewer than 10 tiles
  }

  @Test
  public void testStartNewRound_ContainsStartingTile() {

    assertEquals(tableCenter.take(0).get(0), Tile.STARTING_PLAYER);
  }

  @Test
  public void testStartNewRound_ResetsTableCenter() {

    // Add tiles and take once.
    tableCenter.put(List.of(Tile.BLUE, Tile.RED));
    tableCenter.take(1);

    // Start a new round.
    tableCenter.startNewRound();

    // Should not be empty due to starting player tile.
    assertFalse(tableCenter.isEmpty());

    // First tile should be the starting player tile.
    assertEquals(Tile.STARTING_PLAYER, tableCenter.take(0).get(0));
  }
}

