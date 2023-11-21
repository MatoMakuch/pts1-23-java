package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounter;

import java.util.Arrays;
import java.util.Objects;

class RowCompletionCounter implements PointsCounter {

  @Override
  public Points countPoints(Tile[][] wall) {

    int score = 0;

    for (Tile[] row : wall) {

      if (row != null && Arrays.stream(row).allMatch(Objects::nonNull)) {

        score += 2;
      }
    }

    return new Points(score);
  }
}