package sk.uniba.fmph.dcs;

class ColorCompletionCounter implements PointsCounter {

  @Override
  public Points countPoints(Tile[][] wall) {

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