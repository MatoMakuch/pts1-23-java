package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class UsedTiles implements UsedTilesInterface {

  private List<Tile> usedTiles;

  public UsedTiles() {

    usedTiles = new ArrayList<>();
  }

  @Override
  public void give(Collection<Tile> tiles) {

    usedTiles.addAll(tiles);
  }
}
