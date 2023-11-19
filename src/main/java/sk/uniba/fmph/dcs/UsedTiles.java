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

  public List<Tile> getUsedTiles() {

    return new ArrayList<>(usedTiles);
  }

  // Memento inner class for UsedTiles' state
  public static class UsedTilesState {
    private final String tiles;

    private UsedTilesState(List<Tile> tiles) {

      this.tiles = Tile.toString(tiles);
    }
  }

  public UsedTilesState saveState() {

    return new UsedTilesState(usedTiles);
  }

  public void restoreState(UsedTilesState state) {

    this.usedTiles = Tile.fromString(state.tiles);
  }
}
