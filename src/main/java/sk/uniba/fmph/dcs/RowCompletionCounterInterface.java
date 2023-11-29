package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

import java.util.Arrays;
import java.util.Objects;

class RowCompletionCounterInterface implements PointsCounterInterface {

  private static final int POINTS_PER_ROW = 2;

  @Override
  public Points countPoints(final Tile[][] wall) {

    int score = 0;

    for (Tile[] row : wall) {

      if (row != null && Arrays.stream(row).allMatch(Objects::nonNull)) {

        score += POINTS_PER_ROW;
      }
    }

    return new Points(score);
  }
}
