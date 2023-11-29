package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TileStateInterface;

import java.util.List;
import java.util.Objects;

public class GameFinished {

  public static FinishRoundResult gameFinished(final List<TileStateInterface> wallLines) {

    boolean isRowCompleted = false;

    for (TileStateInterface line : wallLines) {

      if (Tile.fromString(line.getState()).stream().allMatch(Objects::nonNull)) {

        isRowCompleted = true;
        break;
      }
    }

    if (isRowCompleted) {

      return FinishRoundResult.GAME_FINISHED;

    } else {

      return FinishRoundResult.NORMAL;
    }
  }
}