package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private final List<PatternLine> patternLines;
  private final List<WallLine> wallLines;
  private final Floor floor;

  public Board(Floor floor) {

    this.floor = floor;
    this.wallLines = new ArrayList<>();
    this.patternLines = new ArrayList<>();

    // Get the list of colors from the Tile enum.
    var colors = new Tile[] { Tile.RED, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK };

    // Generate the wall pattern where each row is a unique permutation.
    for (int i = 0; i < colors.length; i++) {

      Tile[] pattern = new Tile[colors.length];

      for (int j = 0; j < colors.length; j++) {

        pattern[j] = colors[(i + j) % colors.length];
      }

      var wallLine = new WallLine(pattern);

      if (i > 0) {

        wallLine.setLineUp(wallLines.get(i - 1));

        wallLines.get(i - 1).setLineDown(wallLine);
      }

      wallLines.add(wallLine);
    }

    // Create PatternLines for each WallLine
    for (int i = 0; i < wallLines.size(); i++) {

      patternLines.add(new PatternLine(wallLines.get(i), floor, i + 1));
    }
  }

  public void put(int destinationIndex, List<Tile> tiles) {

    patternLines.get(destinationIndex).put(tiles);
  }

  public FinishRoundResult finishRound() {

    // TODO: Implement the logic for finishing the round.

    return FinishRoundResult.GAME_FINISHED;
  }

  public void endGame() {
    // Logic for the end of the game, including final scoring.
  }

  public String state() {

    StringBuilder builder = new StringBuilder();

    builder.append("PatternLine:");

    for (int i = 0; i < patternLines.size(); i++) {

      builder.append(patternLines.get(i).state());

      if (i < patternLines.size() - 1) {

        builder.append(",");
      }
      else {

        builder.append(";");
      }
    }

    builder.append("WallLine:");
    for (int i = 0; i < wallLines.size(); i++) {

      builder.append(wallLines.get(i).state());

      if (i < wallLines.size() - 1) {

        builder.append(",");
      }
      else {

        builder.append(";");
      }
    }

    builder.append("Floor:");
    builder.append(floor.state());
    builder.append(";");

    return builder.toString();

  }
}