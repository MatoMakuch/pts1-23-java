package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.HashSet;
import java.util.Set;

public class FakeWallLine implements WallLineInterface {
  
  private final Set<Tile> acceptableTiles;
  private WallLineInterface lineUp;
  private WallLineInterface lineDown;

  public FakeWallLine() {

    acceptableTiles = new HashSet<>();
  }

  public void setCanPutTile(final Tile tile, final boolean canPut) {

    if (canPut) {

      acceptableTiles.add(tile);
    } else {

      acceptableTiles.remove(tile);
    }
  }

  @Override
  public WallLineInterface getLineUp() {

    return lineUp;
  }

  @Override
  public void setLineUp(final WallLineInterface lineUp) {

    this.lineUp = lineUp;
  }

  @Override
  public WallLineInterface getLineDown() {

    return lineDown;
  }

  public void setLineDown(final WallLineInterface lineDown) {

    this.lineDown = lineDown;
  }

  @Override
  public boolean canPutTile(final Tile tile) {

    return acceptableTiles.contains(tile);
  }

  @Override
  public Points putTile(final Tile tile) {

    if (!canPutTile(tile)) {
      throw new IllegalStateException("Tile cannot be placed on this wall line.");
    }

    // Assuming 1 point per tile for simplicity in this fake implementation.
    return new Points(1);
  }

  @Override
  public String getState() {

    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public void setState(final String state) {

    throw new UnsupportedOperationException("Not implemented.");
  }
}