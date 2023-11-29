package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BoardInterface;
import sk.uniba.fmph.dcs.interfaces.TakeTurnInterface;
import sk.uniba.fmph.dcs.interfaces.TileStateInterface;

import java.util.Collections;
import java.util.List;

public class Board implements BoardInterface {
  private final TakeTurnInterface floor;
  private final List<TakeTurnInterface> patternLines;
  private final List<TileStateInterface> wallLines;
  private final FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
  private final Points points = new Points(0);

  public Board(final TakeTurnInterface floor, final List<TakeTurnInterface> patternLines, final List<TileStateInterface> wallLines) {

    this.floor = floor;
    this.patternLines = patternLines;
    this.wallLines = wallLines;
  }

  @Override
  public Points getPoints() {

    return points;
  }

  @Override
  public void put(final List<Tile> tiles, final int destinationIndex) {

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

    for (TakeTurnInterface patternLine : patternLines) {

      points.add(patternLine.finishRound());
    }

    points.add(floor.finishRound());

    return GameFinished.gameFinished(wallLines);
  }

  @Override
  public void endGame() {

    final Tile[][] wall = wallLines.stream().map(line -> Tile.fromString(line.getState()).toArray(Tile[]::new)).toArray(Tile[][]::new);

    final Points finalPoints = finalPointsCalculation.getPoints(wall);

    points.add(finalPoints);
  }
}
