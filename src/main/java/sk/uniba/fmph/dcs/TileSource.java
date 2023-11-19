package sk.uniba.fmph.dcs;

import java.util.List;

public interface TileSource {
  List<Tile> take(int index);

  boolean isEmpty();

  void startNewRound();

  String state();
}
