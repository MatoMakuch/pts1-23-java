package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.GameObserver;

public interface GameInterface {
  GameObserver getGameObserver();
  boolean isGameOver();
  String onTurn();
  void take(int sourceIndex, int tileIndex, int destinationIndex);
}
