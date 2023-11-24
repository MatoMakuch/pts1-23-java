package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.SourcePath;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.TableAreaInterface;

import java.util.List;
import java.util.Map;

public class FakeTableArea implements TableAreaInterface {

  private final Map<SourcePath, List<Tile>> tiles;
  private boolean roundEnd = false;

  public FakeTableArea(Map<SourcePath, List<Tile>> tiles) {

    this.tiles = tiles;
  }

  public void setRoundEnd(boolean roundEnd) {

    this.roundEnd = roundEnd;
  }

  @Override
  public List<Tile> take(SourcePath sourcePath) {

    return tiles.get(sourcePath);
  }

  @Override
  public boolean isRoundEnd() {

    return this.roundEnd;
  }

  @Override
  public void startNewRound() {

    this.roundEnd = false;
  }
}
