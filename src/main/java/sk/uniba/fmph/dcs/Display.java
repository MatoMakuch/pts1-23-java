package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.FloorStateInterface;
import sk.uniba.fmph.dcs.interfaces.PlayerInterface;
import sk.uniba.fmph.dcs.interfaces.TileStateInterface;

import java.util.List;

public class Display {

  private static final int MAX_LINE_LENGTH = 50;
  private static final int NAME_START_INDEX = 0;
  private static final int POINTS_START_INDEX = 2;
  private static final int PATTERN_LINES_START_INDEX = 4;
  private static final int WALL_LINES_START_INDEX = 11;
  private static final int FLOOR_START_INDEX = 18;

  public static void display(final TileStateInterface tableCenter, final List<TileStateInterface> factories, final List<PlayerInterface> players, final List<List<TileStateInterface>> patternLines, final List<List<TileStateInterface>> wallLines, final List<FloorStateInterface> floors) {

    System.out.print(new GridHorizontalSeparator(MAX_LINE_LENGTH));

    final GridDisplay factoriesGrid = new GridDisplay(MAX_LINE_LENGTH, factories.size(), 5);
    for (int i = 0; i < factories.size(); i++) {

      factoriesGrid.addText(i, 0, "F" + (i + 1) + ":");

      final List<Tile> tiles = Tile.fromString(factories.get(i).getState());

      for (int j = 0; j < tiles.size(); j++) {

        factoriesGrid.addText(i, 1 + j, tiles.get(j).toString());
      }
    }
    System.out.print(factoriesGrid);

    System.out.print(new GridHorizontalSeparator(MAX_LINE_LENGTH));

    final GridDisplay tableCenterGrid = new GridDisplay(MAX_LINE_LENGTH, 1, 1);
    tableCenterGrid.addText(0, 0, "Table center: " + tableCenter.getState());
    System.out.print(tableCenterGrid);

    System.out.print(new GridHorizontalSeparator(MAX_LINE_LENGTH));

    final GridDisplay playerGrid = new GridDisplay(MAX_LINE_LENGTH, 2, 26);
    for (int i = 0; i < players.size(); i++) {

      final PlayerInterface player = players.get(i);

      playerGrid.addText(i, NAME_START_INDEX, players.get(i).getName() + ":");

      playerGrid.addText(i, POINTS_START_INDEX, "Points: " + player.getPoints().getValue());

      playerGrid.addText(i, PATTERN_LINES_START_INDEX, "Pattern lines:");
      for (int j = 0; j < patternLines.get(i).size(); j++) {

        playerGrid.addText(i, PATTERN_LINES_START_INDEX + 1 + j, "P" + (j + 1) + ": " + patternLines.get(i).get(j));
      }

      playerGrid.addText(i, WALL_LINES_START_INDEX, "Wall lines:");
      for (int j = 0; j < wallLines.get(i).size(); j++) {

        playerGrid.addText(i, WALL_LINES_START_INDEX + 1 + j, "W" + (j + 1) + ": " + wallLines.get(i).get(j));
      }

      final List<Points> floorPattern = floors.get(i).getPointPattern();
      final List<Tile> floorTiles = Tile.fromString(floors.get(i).getState());
      playerGrid.addText(i, FLOOR_START_INDEX, "Floor:");
      for (int j = 0; j < floorPattern.size(); j++) {

        final String tileString = j < floorTiles.size() ? floorTiles.get(j).toString() : "";

        playerGrid.addText(i, FLOOR_START_INDEX + 1 + j, "F" + floorPattern.get(j).getValue() + ": " + tileString);
      }
    }
    System.out.print(playerGrid);

    System.out.print(new GridHorizontalSeparator(MAX_LINE_LENGTH));
  }
}
