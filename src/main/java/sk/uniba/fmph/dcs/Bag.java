package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TakeAllTilesInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag implements BagInterface {

  private static final int TILE_COUNT = 20;
  private final TakeAllTilesInterface usedTiles;
  private List<Tile> tiles = new ArrayList<>();

  public Bag(final TakeAllTilesInterface usedTiles) {

    this.usedTiles = usedTiles;
  }

  @Override
  public void fillBag(final int seed) {

    this.tiles.clear();

    for (Tile tile : Tile.values()) {

      if (tile != Tile.STARTING_PLAYER) {

        for (int i = 0; i < TILE_COUNT; i++) {

          tiles.add(tile);
        }
      }
    }

    Collections.shuffle(tiles, new Random(seed));
  }

  @Override
  public void startNewRound() {

    tiles.addAll(usedTiles.takeAll());
  }

  @Override
  public List<Tile> take(final int count) {

    if (count > tiles.size()) {

      throw new IllegalArgumentException("Not enough tiles in the bag");
    }

    List<Tile> pickedTiles = new ArrayList<>();
    for (int i = 0; i < count; i++) {

      pickedTiles.add(tiles.remove(0));
    }

    return pickedTiles;
  }

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) {
    tiles = Tile.fromString(state);
  }

  @Override
  public String toString() {
    return getState();
  }
}
