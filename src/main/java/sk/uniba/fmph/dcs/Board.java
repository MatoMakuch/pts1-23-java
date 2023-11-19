package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
  private List<PatternLine> patternLines = new ArrayList<>();
  private List<WallLine> wallLines = new ArrayList<>();
  private final Floor floor;
  private final FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
  private final Points points = new Points(0);

  public Board() {

    this.floor = new Floor();

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

  public static class BoardState {
    private Floor.FloorState floorState;
    private List<WallLine.WallLineState> wallLineStates;
    private List<PatternLine.PatternLineState> patternLineStates;
    private Points points;
  }

  public BoardState saveState() {

    BoardState state = new BoardState();

    state.floorState = floor.saveState();

    state.wallLineStates = new ArrayList<>();
    for (WallLine wallLine : wallLines) {

      state.wallLineStates.add(wallLine.saveState());
    }

    state.patternLineStates = new ArrayList<>();
    for (PatternLine patternLine : patternLines) {

      state.patternLineStates.add(patternLine.saveState());
    }

    state.points = points;

    return state;
  }

  public void restoreState(BoardState state) {

    // Restore the state of the floor.
    this.floor.restoreState(state.floorState);

    // Restore the states of the wall lines.
    for (int i = 0; i < state.wallLineStates.size(); i++) {

      if (i < wallLines.size()) {

        wallLines.get(i).restoreState(state.wallLineStates.get(i));
      }
    }

    // Restore the states of the pattern lines.
    for (int i = 0; i < state.patternLineStates.size(); i++) {

      if (i < patternLines.size()) {

        patternLines.get(i).restoreState(state.patternLineStates.get(i));
      }
    }

    // Restore points
    this.points.setValue(state.points.getValue());
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

    for (PatternLine patternLine : patternLines) {

      points.add(patternLine.finishRound());
    }

    return GameFinished.gameFinished(wallLines);
  }

  public void endGame() {

    Tile[][] wall = wallLines.stream()
        .map(WallLine::getTiles)
        .toArray(Tile[][]::new);

    Points finalPoints = finalPointsCalculation.getPoints(wall);

    points.add(finalPoints);
  }
}
