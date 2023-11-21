package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;

import java.util.List;
import java.util.ArrayList;

public class FakeTableCenter implements TableCenterInterface {
  private List<Tile> tiles;

  public FakeTableCenter() {

    tiles = new ArrayList<>();
  }

  @Override
  public void add(List<Tile> tiles) {

    this.tiles.addAll(tiles);
  }

  @Override
  public List<Tile> take(int index) {

    throw new UnsupportedOperationException("Not supported");
  }

  @Override
  public boolean isEmpty() {
    return tiles.isEmpty();
  }

  @Override
  public void startNewRound() {
    tiles.clear();
  }

  @Override
  public String getState() {
    return Tile.toString(tiles);
  }

  @Override
  public void setState(String state) {
    tiles = Tile.fromString(state);
  }
}

