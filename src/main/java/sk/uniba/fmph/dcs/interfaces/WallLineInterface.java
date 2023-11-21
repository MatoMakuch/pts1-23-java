package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

public interface WallLineInterface {
  /**
   * Determines if a tile can be placed on this wall line.
   *
   * @param tile the tile to check
   * @return true if the tile can be placed, false otherwise
   */
  boolean canPutTile(Tile tile);

  /**
   * Places a tile on this wall line and calculates the points scored.
   *
   * @param tile the tile to be placed
   * @return the points scored by placing the tile
   */
  Points putTile(Tile tile);

  /**
   * Retrieves the state of the tiles on the wall line.
   *
   * @return an array of tiles representing the wall line's state
   */
  Tile[] getTiles();
}

