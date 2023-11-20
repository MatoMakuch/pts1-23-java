package sk.uniba.fmph.dcs;

interface GameInterface {
  GameObserver getGameObserver();
  String onTurn();
  void take(int sourceIndex, int tileIndex, int destinationIndex);
}
