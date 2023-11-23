package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.fake.FakeWallLine;
import sk.uniba.fmph.dcs.interfaces.TakeAllTilesInterface;
import sk.uniba.fmph.dcs.interfaces.UsedTilesInterface;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {
  private PatternLine patternLine;
  private FakeWallLine fakeWallLine;
  private Floor floor;
  private final Tile testTile = Tile.RED;

  @BeforeEach
  public void setUp() {

    UsedTilesInterface usedTiles = new UsedTiles();

    fakeWallLine = new FakeWallLine();
    fakeWallLine.setCanPutTile(testTile, true);

    floor = new Floor(usedTiles, List.of(
        new Points(1),
        new Points(2),
        new Points(2)
    ));

    patternLine = new PatternLine(usedTiles, floor, fakeWallLine, 3);
  }

  @Test
  public void testPutTiles_ValidTiles_ShouldAddToPatternLine() {

    patternLine.put(Collections.singletonList(testTile));

    assertEquals("R..", patternLine.getState());

    assertEquals(testTile, Tile.fromString(patternLine.getState()).get(0));
  }

  @Test
  public void testPutTiles_InvalidType_ShouldThrowException() {

    patternLine.put(Collections.singletonList(testTile));

    assertThrows(IllegalArgumentException.class, () -> patternLine.put(Collections.singletonList(Tile.BLUE)));
  }

  @Test
  public void testPutTiles_TooManyTiles_ShouldAddToFloors() {

    patternLine.put(Collections.singletonList(testTile));
    patternLine.put(Collections.singletonList(testTile));
    patternLine.put(Collections.singletonList(testTile));

    // Adding one more should go to the floor.
    patternLine.put(Collections.singletonList(testTile));

    // Verify that the floor received the extra tile.
    assertTrue(Tile.fromString(floor.getState()).contains(testTile));
  }

  @Test
  public void testFinishRound_NotFull_ShouldNotAddPoints() {

    patternLine.put(Collections.singletonList(testTile));

    assertEquals(new Points(0), patternLine.finishRound());
  }

  @Test
  public void testFinishRound_Full_ShouldAddPoints() {

    patternLine.put(Collections.singletonList(testTile));
    patternLine.put(Collections.singletonList(testTile));
    patternLine.put(Collections.singletonList(testTile));

    assertEquals(new Points(1), patternLine.finishRound()); // Assuming putting a tile gives 1 point
  }

  @Test
  public void testFinishRound_CannotPutTileOnWall_ShouldThrowException() {

    fakeWallLine.setCanPutTile(testTile, false);

    assertThrows(IllegalStateException.class, () -> {

      // Try to put a tile that the wall line cannot accept.
      patternLine.put(Collections.singletonList(testTile));
    });
  }
}
