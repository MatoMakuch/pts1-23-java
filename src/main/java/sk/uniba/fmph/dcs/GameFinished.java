package sk.uniba.fmph.dcs;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameFinished {

  public static FinishRoundResult gameFinished(List<WallLine> wallLines) {

    boolean isRowCompleted = false;

    for (WallLine line : wallLines) {

      if (Arrays.stream(line.getTiles()).allMatch(Objects::nonNull)) {

        isRowCompleted = true;
        break;
      }
    }

    if (isRowCompleted) {

      return FinishRoundResult.GAME_FINISHED;

    }
    else {

      return FinishRoundResult.NORMAL;
    }
  }
}
