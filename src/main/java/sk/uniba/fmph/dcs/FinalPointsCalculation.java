package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PointsCounter;

import java.util.ArrayList;
import java.util.List;

public class FinalPointsCalculation {
  private List<PointsCounter> counters;

  public FinalPointsCalculation() {

    counters = new ArrayList<>();

    counters.add(new RowCompletionCounter());
    counters.add(new ColumnCompletionCounter());
    counters.add(new ColorCompletionCounter());
  }

  public Points getPoints(Tile[][] wall) {

    var totalPoints = 0;

    for (PointsCounter counter : counters) {

      totalPoints += counter.countPoints(wall).getValue();
    }

    return new Points(totalPoints);
  }
}
