package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class UsedTiles implements UsedTilesInterface {
  private static UsedTiles instance = null;
  private List<Tile> usedTiles;

  private UsedTiles() {
    usedTiles = new ArrayList<>();
  }

  public static UsedTiles getInstance() {
    if (instance == null) {
      instance = new UsedTiles();
    }
    return instance;
  }

  @Override
  public void give(Collection<Tile> tiles) {
    usedTiles.addAll(tiles);
  }

  // If you need to access the used tiles, add a method for it
  public List<Tile> getUsedTiles() {
    return new ArrayList<>(usedTiles); // Returning a copy to maintain encapsulation
  }
}
