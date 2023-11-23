package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;

import java.util.List;

public interface FloorStateInterface extends TileStateInterface {
  List<Points> getPointPattern();
}
