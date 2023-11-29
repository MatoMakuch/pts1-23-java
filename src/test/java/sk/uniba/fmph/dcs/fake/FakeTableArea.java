package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.SourcePath;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.TableAreaInterface;

import java.util.List;
import java.util.Map;

public class FakeTableArea implements TableAreaInterface {

  private final Map<SourcePath, List<Tile>> tiles;
  private boolean roundEnd = false;

  public FakeTableArea(final Map<SourcePath, List<Tile>> tiles) {

    this.tiles = tiles;
  }

  @Override
  public List<Tile> take(final SourcePath sourcePath) {

    return tiles.get(sourcePath);
  }

  @Override
  public boolean isRoundEnd() {

    return this.roundEnd;
  }

  public void setRoundEnd(final boolean roundEnd) {

    this.roundEnd = roundEnd;
  }

  @Override
  public void startNewRound() {

    this.roundEnd = false;
  }
}
