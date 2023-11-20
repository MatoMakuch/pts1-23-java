package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag {
  private static Bag instance = null;
  private List<Tile> tiles = new ArrayList<>();

  private Bag() {

  }

  public static Bag getInstance() {
    if (instance == null) {
      instance = new Bag();
    }
    return instance;
  }

  public static class BagState {
    private String tiles;

    private BagState(List<Tile> tiles) {
      this.tiles = Tile.toString(tiles);
    }
  }

  public BagState saveState() {
    return new BagState(tiles);
  }

  public void restoreState(BagState state) {
    tiles = Tile.fromString(state.tiles);
  }

  public void fillBag() {

    this.tiles.clear();
    for (Tile tile : Tile.values()) {
      if (tile != Tile.STARTING_PLAYER) {
        for (int i = 0; i < 20; i++) {
          tiles.add(tile);
        }
      }
    }
    Collections.shuffle(tiles, new Random());
  }

  public void fillBag(List<Tile> tiles) {

    this.tiles.clear();
    this.tiles.addAll(tiles);
  }

  public List<Tile> getTiles(int count) {
    if (count > tiles.size()) {
      throw new IllegalArgumentException("Not enough tiles in the bag");
    }
    List<Tile> pickedTiles = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      pickedTiles.add(tiles.remove(0));
    }
    return pickedTiles;
  }
}
