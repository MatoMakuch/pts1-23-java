package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.PatternLineInterface;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
  private final Floor floor;
  private final List<PatternLineInterface> patternLines = new ArrayList<>();
  private final List<WallLineInterface> wallLines = new ArrayList<>();
  private final FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
  private final Points points = new Points(0);

  public Board(List<Points> pointPattern) {

    this.floor = new Floor(pointPattern);

    TilePermutationIterator iterator = new TilePermutationIterator();

    int i = 0; // Initialize the counter.
    while (iterator.hasNext()) {

      final WallLine newWallLine = new WallLine(iterator.next());

      if (i > 0) { // For all but the first WallLine.

        newWallLine.setLineUp(wallLines.get(i - 1));
        wallLines.get(i - 1).setLineDown(newWallLine);
      }

      wallLines.add(newWallLine);

      patternLines.add(new PatternLine(wallLines.get(i), floor, i + 1));

      i++; // Increment the counter.
    }
  }

  //#region Board state management

  public static class BoardState {
    private final String floorState;
    private final List<String> wallLineStates;
    private final List<String> patternLineStates;
    private final Points points;

    private BoardState(Floor floor, List<PatternLineInterface> patternLines, List<WallLineInterface> wallLines, Points points) {

      this.floorState = floor.getState();

      this.wallLineStates = new ArrayList<>();
      for (WallLineInterface wallLine : wallLines) {

        this.wallLineStates.add(wallLine.getState());
      }

      this.patternLineStates = new ArrayList<>();
      for (PatternLineInterface patternLine : patternLines) {

        this.patternLineStates.add(patternLine.getState());
      }

      this.points = points;
    }
  }

  public BoardState saveState() {

    return new BoardState(floor, patternLines, wallLines, points);
  }

  public void restoreState(BoardState state) {

    // Restore the state of the floor.
    this.floor.setState(state.floorState);

    // Restore the states of the wall lines.
    for (int i = 0; i < state.wallLineStates.size(); i++) {

      if (i < wallLines.size()) {

        wallLines.get(i).setState(state.wallLineStates.get(i));
      }
    }

    // Restore the states of the pattern lines.
    for (int i = 0; i < state.patternLineStates.size(); i++) {

      if (i < patternLines.size()) {

        patternLines.get(i).setState(state.patternLineStates.get(i));
      }
    }

    // Restore points
    this.points.setValue(state.points.getValue());
  }

  public List<String> getPatternLineStates() {

    return patternLines.stream().map(PatternLineInterface::getState).collect(Collectors.toList());
  }

  public List<String> getWallLineStates() {

    return wallLines.stream().map(WallLineInterface::getState).collect(Collectors.toList());
  }

  //#endregion

  public Floor getFloor() {

    return floor;
  }

  public Points getPoints() {

    return points;
  }

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

  public FinishRoundResult finishRound() {

    for (PatternLineInterface patternLine : patternLines) {

      points.add(patternLine.finishRound());
    }

    points.add(floor.finishRound());

    return GameFinished.gameFinished(wallLines);
  }

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
