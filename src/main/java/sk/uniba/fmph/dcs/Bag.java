package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag {
  private List<Tile> tiles = new ArrayList<>();

  public Bag() {

    fillBag();
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

  private void fillBag() {

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

  public String state() {

    StringBuilder stateBuilder = new StringBuilder();
    for (Tile tile : tiles) {
      stateBuilder.append(tile.toString());
    }
    return stateBuilder.toString();
  }
}
