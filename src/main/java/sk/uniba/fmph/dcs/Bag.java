package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TakeAllTilesInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag implements BagInterface {

  private final TakeAllTilesInterface usedTiles;
  private List<Tile> tiles = new ArrayList<>();

  public Bag(TakeAllTilesInterface usedTiles) {

    this.usedTiles = usedTiles;
  }

  @Override
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

  @Override
  public void startNewRound() {

    tiles.addAll(usedTiles.takeAll());
  }

  @Override
  public List<Tile> take(int count) {

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
  public void setState(String state) { tiles = Tile.fromString(state); }

  @Override
  public String toString() { return getState(); }
}
