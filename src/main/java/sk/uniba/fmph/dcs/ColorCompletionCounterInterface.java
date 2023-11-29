package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

class ColorCompletionCounterInterface implements PointsCounterInterface {

  private static final int POINTS_PER_COLOR = 10;

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

        score += POINTS_PER_COLOR;
      }
    }

    return new Points(score);
  }
}
