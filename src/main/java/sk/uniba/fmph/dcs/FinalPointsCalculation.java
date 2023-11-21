package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounterInterface;

import java.util.ArrayList;
import java.util.List;

public class FinalPointsCalculation {
  private List<PointsCounterInterface> counters;

  public FinalPointsCalculation() {

    counters = new ArrayList<>();

    counters.add(new RowCompletionCounterInterface());
    counters.add(new ColumnCompletionCounterInterface());
    counters.add(new ColorCompletionCounterInterface());
  }

  public Points getPoints(Tile[][] wall) {

    var totalPoints = 0;

    for (PointsCounterInterface counter : counters) {

      totalPoints += counter.countPoints(wall).getValue();
    }

    return new Points(totalPoints);
  }
}
