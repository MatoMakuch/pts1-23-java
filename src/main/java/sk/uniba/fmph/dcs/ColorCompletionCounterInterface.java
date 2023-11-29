package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

class ColorCompletionCounterInterface implements PointsCounterInterface {

  @Override
  public Points countPoints(final Tile[][] wall) {

    int score = 0;

    int[] colorCounts = new int[Tile.values().length];

    for (Tile[] row : wall) {

      for (Tile tile : row) {

        if (tile != null) {

          colorCounts[tile.ordinal()]++;
        }
      }
    }

    for (int colorCount : colorCounts) {

      if (colorCount == wall.length) {

        score += 10;
      }
    }

    return new Points(score);
  }
}