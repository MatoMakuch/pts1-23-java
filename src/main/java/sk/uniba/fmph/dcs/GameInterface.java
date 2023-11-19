package sk.uniba.fmph.dcs;

interface GameInterface {
  String onTurn();
  void take(int sourceIndex, int tileIndex, int destinationIndex);
}
