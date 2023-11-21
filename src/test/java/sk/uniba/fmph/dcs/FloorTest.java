package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FloorTest {
  private Floor floor;

  @Before
  public void setUp() {

    floor = new Floor(List.of(
        new Points(1),
        new Points(2),
        new Points(2)
    ));
  }

  @Test
  public void test_tiles() {

    // Floor should be empty when created.
    UsedTiles.getInstance().setState("");

    List<Tile> tiles = new ArrayList<>();

    tiles.add(Tile.STARTING_PLAYER);
    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);
    tiles.add(Tile.RED);

    assertEquals("Floor should be empty when created.", "", floor.getState());

    floor.put(tiles);

    assertEquals("Floor should contain tiles we put on it.", "SRGR", floor.getState());

    Points points = floor.finishRound();

    assertEquals("Floor should be empty after the round is finished.", "", floor.getState());
    assertEquals(
        "Incorrect points calculation when there are more tiles than pattern size",
        7,
        points.getValue());
    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), Tile.fromString(UsedTiles.getInstance().getState()).toArray());

    floor.put(List.of(Tile.RED));
    floor.put(List.of(Tile.GREEN));
    floor.put(new ArrayList<>());

    assertEquals("Floor should contain tiles we put on it.", "RG", floor.getState());

    Points points2 = floor.finishRound();

    assertEquals("Floor should be empty after the round is finished.", "", floor.getState());
    assertEquals(
        "Incorrect points calculation when there are less tiles than pattern size",
        3,
        points2.getValue());

    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);

    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), Tile.fromString(UsedTiles.getInstance().getState()).toArray());
  }
}
