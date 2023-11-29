package sk.uniba.fmph.dcs.interfaces;

public interface BagInterface extends TakeMultipleTilesInterface, StartNewRoundInterface, TileStateInterface {
  void fillBag(int seed);
}
