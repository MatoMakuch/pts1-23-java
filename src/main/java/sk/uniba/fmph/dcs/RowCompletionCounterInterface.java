package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

import java.util.Arrays;
import java.util.Objects;

class RowCompletionCounterInterface implements PointsCounterInterface {

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