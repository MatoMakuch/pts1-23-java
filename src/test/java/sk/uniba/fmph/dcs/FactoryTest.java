package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.fake.FakeTableCenter;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {
  private Factory factory;
  private TableCenterInterface fakeTableCenter;

  @BeforeEach
  public void setUp() {

    Bag.getInstance().setState("RRGGBBYYLL");

    fakeTableCenter = new FakeTableCenter();

    factory = new Factory(fakeTableCenter);
  }

  @Test
  public void testTakeTiles_ReturnsCorrectTilesAndMovesOthersToTableCenter() {

    // Pre-fill the factory with a set of tiles.
    List<Tile> initialTiles = List.of(Tile.RED, Tile.RED, Tile.BLUE, Tile.YELLOW);
    factory.setState(Tile.toString(initialTiles));

    // Take red tiles from the factory.
    List<Tile> takenTiles = factory.take(0); // Assuming 0 is the index for Tile.RED.

    assertEquals(2, takenTiles.size()); // Should take two red tiles.
    assertTrue(takenTiles.stream().allMatch(tile -> tile == Tile.RED));
    assertTrue(factory.isEmpty());

    // Verify that blue and yellow tiles were moved to the table center.
    List<Tile> tableCenterTiles = Tile.fromString(fakeTableCenter.getState());
    assertEquals(2, tableCenterTiles.size());
    assertTrue(tableCenterTiles.contains(Tile.BLUE));
    assertTrue(tableCenterTiles.contains(Tile.YELLOW));
  }

  @Test
  public void testTakeTiles_InvalidIndex_ThrowsException() {

    assertThrows(IllegalArgumentException.class, () -> factory.take(-1));
    assertThrows(IllegalArgumentException.class, () -> factory.take(4));
  }

  @Test
  public void testStartNewRound_RefillsFactory() {

    // Factory should be empty initially.
    assertTrue(factory.isEmpty());

    factory.startNewRound();

    // Factory should be refilled and not empty.
    assertFalse(factory.isEmpty());
  }
}

