package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.GameObserver;
import sk.uniba.fmph.dcs.SourcePath;

public interface GameInterface {
  void start();
  GameObserver getGameObserver();
  boolean isGameFinished();
  String onTurn();
  void take(SourcePath sourcePath, int destinationIndex);
}
