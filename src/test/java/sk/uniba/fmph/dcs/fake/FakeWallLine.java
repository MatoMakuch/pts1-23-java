package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.HashSet;
import java.util.Set;

public class FakeWallLine implements WallLineInterface {
  private final Set<Tile> acceptableTiles;

  public FakeWallLine() {

    acceptableTiles = new HashSet<>();
  }

  public void setCanPutTile(Tile tile, boolean canPut) {

    if (canPut) {

      acceptableTiles.add(tile);
    }
    else {

      acceptableTiles.remove(tile);
    }
  }

  @Override
  public boolean canPutTile(Tile tile) {

    return acceptableTiles.contains(tile);
  }

  @Override
  public Points putTile(Tile tile) {

    if (!canPutTile(tile)) {
      throw new IllegalStateException("Tile cannot be placed on this wall line.");
    }

    // Assuming 1 point per tile for simplicity in this fake implementation.
    return new Points(1);
  }

  @Override
  public Tile[] getTiles() {

    throw new UnsupportedOperationException("Not implemented.");
  }
}