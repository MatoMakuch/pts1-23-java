package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

import java.util.Arrays;

class ColumnCompletionCounterInterface implements PointsCounterInterface {

  private static final int POINTS_PER_COLUMN = 7;

  @Override
  public Points countPoints(final Tile[][] wall) {

    int score = 0;

    if (wall.length == 0) {

      return new Points(score);
    }

    int colCount = wall[0].length;

    for (int col = 0; col < colCount; col++) {

      final int columnIndex = col;

      if (Arrays.stream(wall).allMatch(row -> row[columnIndex] != null)) {

        score += POINTS_PER_COLUMN;
      }
    }

    return new Points(score);
  }
}
