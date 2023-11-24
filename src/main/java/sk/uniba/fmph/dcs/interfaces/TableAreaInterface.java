package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.SourcePath;
import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TableAreaInterface {
  List<Tile> take(SourcePath sourcePath);
  boolean isRoundEnd();
  void startNewRound();
}
