package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TileSource implements TileStateInterface {

  protected List<Tile> tiles = new ArrayList<>();

  public List<Tile> getTiles() {

    return Collections.unmodifiableList(tiles);
  }

  public abstract List<Tile> take(int index);

  public abstract boolean isEmpty();

  public abstract void startNewRound();

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) { tiles = Tile.fromString(state); }
}
