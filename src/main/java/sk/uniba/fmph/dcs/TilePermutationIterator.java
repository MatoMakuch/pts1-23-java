package sk.uniba.fmph.dcs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TilePermutationIterator implements Iterator<Tile[]> {

  private final Tile[] colors = new Tile[]{Tile.RED, Tile.GREEN, Tile.YELLOW, Tile.BLUE, Tile.BLACK};
  private int currentIndex = 0;

  @Override
  public boolean hasNext() {
    return currentIndex < colors.length;
  }

  @Override
  public Tile[] next() {

    if (!hasNext()) {

      throw new NoSuchElementException("No more permutations available.");
    }

    final Tile[] pattern = new Tile[colors.length];
    for (int j = 0; j < colors.length; j++) {

      pattern[j] = colors[(currentIndex + j) % colors.length];
    }

    currentIndex++;

    return pattern;
  }
}
