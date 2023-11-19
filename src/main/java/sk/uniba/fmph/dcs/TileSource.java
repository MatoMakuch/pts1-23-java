package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TileSource {

  protected List<Tile> tiles = new ArrayList<>();
  public List<Tile> getTiles() {

    return Collections.unmodifiableList(tiles);
  }
  public abstract List<Tile> take(int index);
  public abstract boolean isEmpty();
  public abstract void startNewRound();
  public TileSourceState saveState() {

    return new TileSourceState(tiles);
  }
  public void restoreState(TileSourceState state) {

    tiles = Tile.fromString(state.tiles);
  }
  public static class TileSourceState {

    private String tiles;

    private TileSourceState(List<Tile> tiles) {

      StringBuilder builder = new StringBuilder();

      for (Tile tile : tiles) {

        builder.append(tile.toString());
      }

      this.tiles = builder.toString();
    }
  }
}
