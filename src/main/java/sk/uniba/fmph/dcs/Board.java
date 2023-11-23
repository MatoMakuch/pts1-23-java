package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.Collections;
import java.util.List;

public class Board implements BoardInterface {
  private final TurnInterface floor;
  private final List<TurnInterface> patternLines;
  private final List<TileStateInterface> wallLines;
  private final FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
  private final Points points = new Points(0);

  public Board(TurnInterface floor, List<TurnInterface> patternLines, List<TileStateInterface> wallLines) {

    this.floor = floor;
    this.patternLines = patternLines;
    this.wallLines = wallLines;
  }

  @Override
  public Points getPoints() {

    return points;
  }

  @Override
  public void put(List<Tile> tiles, int destinationIndex) {

    if (destinationIndex == -1) {

      floor.put(tiles);

      return;
    }

    if (tiles.contains(Tile.STARTING_PLAYER)) {

      tiles.remove(Tile.STARTING_PLAYER);
      floor.put(Collections.singleton(Tile.STARTING_PLAYER));
    }

    patternLines.get(destinationIndex).put(tiles);
  }

  @Override
  public FinishRoundResult finishRound() {

    for (TurnInterface patternLine : patternLines) {

      points.add(patternLine.finishRound());
    }

    points.add(floor.finishRound());

    return GameFinished.gameFinished(wallLines);
  }

  @Override
  public void endGame() {

    Tile[][] wall = wallLines.stream()
        .map(line ->
            Tile.fromString(line.getState()).stream()
                .toArray(Tile[]::new))
        .toArray(Tile[][]::new);

    Points finalPoints = finalPointsCalculation.getPoints(wall);

    points.add(finalPoints);
  }
}
