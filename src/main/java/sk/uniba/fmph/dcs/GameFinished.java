package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameFinished {

  public static FinishRoundResult gameFinished(List<WallLineInterface> wallLines) {

    boolean isRowCompleted = false;

    for (WallLineInterface line : wallLines) {

      if (Tile.fromString(line.getState()).stream().allMatch(Objects::nonNull)) {

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
