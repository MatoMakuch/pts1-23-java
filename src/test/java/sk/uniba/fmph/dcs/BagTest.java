package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.interfaces.UsedTilesInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

  @Test
  void testDrawingSequence() {

    UsedTilesInterface usedTiles = new UsedTiles();

    Bag bag = new Bag(usedTiles);
    bag.setState("RRGGBBYYLL");

    // Draw the first set of 4 tiles.
    List<Tile> firstDrawnTiles = bag.take(4);
    assertEquals(4, firstDrawnTiles.size());
    assertEquals(Tile.RED, firstDrawnTiles.get(0));
    assertEquals(Tile.RED, firstDrawnTiles.get(1));
    assertEquals(Tile.GREEN, firstDrawnTiles.get(2));
    assertEquals(Tile.GREEN, firstDrawnTiles.get(3));

    // Draw the second set of 4 tiles.
    List<Tile> secondDrawnTiles = bag.take(4);
    assertEquals(4, secondDrawnTiles.size());
    assertEquals(Tile.BLUE, secondDrawnTiles.get(0));
    assertEquals(Tile.BLUE, secondDrawnTiles.get(1));
    assertEquals(Tile.YELLOW, secondDrawnTiles.get(2));
    assertEquals(Tile.YELLOW, secondDrawnTiles.get(3));

    // Draw the final set of 2 tiles.
    List<Tile> lastDrawnTiles = bag.take(2);
    assertEquals(2, lastDrawnTiles.size());
    assertEquals(Tile.BLACK, lastDrawnTiles.get(0));
    assertEquals(Tile.BLACK, lastDrawnTiles.get(1));

    // Assert that the bag is now empty.
    assertTrue(bag.take(0).isEmpty());

    // Assert that drawing more tiles than are available throws an exception.
    assertThrows(IllegalArgumentException.class, () -> bag.take(1));
  }
}
