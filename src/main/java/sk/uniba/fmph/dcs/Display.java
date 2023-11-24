package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.FloorStateInterface;
import sk.uniba.fmph.dcs.interfaces.PlayerInterface;
import sk.uniba.fmph.dcs.interfaces.TileStateInterface;

import java.util.List;

public class Display {
  public static void display(
      TileStateInterface tableCenter,
      List<TileStateInterface> factories,
      List<PlayerInterface> players,
      List<List<TileStateInterface>> patternLines,
      List<List<TileStateInterface>> wallLines,
      List<FloorStateInterface> floors) {

    System.out.print(new GridHorizontalSeparator(50));

    GridDisplay factoriesGrid = new GridDisplay(50, factories.size(), 5);
    for (int i = 0; i < factories.size(); i++) {

      factoriesGrid.addText(i, 0, "F" + (i + 1) + ":");

      var tiles = Tile.fromString(factories.get(i).getState());

      for (int j = 0; j < tiles.size(); j++) {

        factoriesGrid.addText(i, 1 + j, tiles.get(j).toString());
      }
    }
    System.out.print(factoriesGrid);

    System.out.print(new GridHorizontalSeparator(50));

    GridDisplay tableCenterGrid = new GridDisplay(50, 1, 1);
    tableCenterGrid.addText(0, 0, "Table center: " + tableCenter.getState());
    System.out.print(tableCenterGrid);

    System.out.print(new GridHorizontalSeparator(50));

    GridDisplay playerGrid = new GridDisplay(50, 2, 26);
    for (int i = 0; i < players.size(); i++) {

      final PlayerInterface player = players.get(i);

      playerGrid.addText(i, 0, players.get(i).getName() + ":");

      playerGrid.addText(i, 2, "Points: " + player.getPoints().getValue());

      playerGrid.addText(i, 4, "Pattern lines:");
      for (int j = 0; j < patternLines.get(i).size(); j++) {

        playerGrid.addText(i, 5 + j, "P" + (j + 1) + ": " + patternLines.get(i).get(j));
      }

      playerGrid.addText(i, 11, "Wall lines:");
      for (int j = 0; j < wallLines.get(i).size(); j++) {

        playerGrid.addText(i, 12 + j, "W" + (j + 1) + ": " + wallLines.get(i).get(j));
      }

      var floorPattern = floors.get(i).getPointPattern();
      var floorTiles = Tile.fromString(floors.get(i).getState());
      playerGrid.addText(i, 18, "Floor:");
      for (int j = 0; j < floorPattern.size(); j++) {

        final String tileString = j < floorTiles.size() ? floorTiles.get(j).toString() : "";

        playerGrid.addText(i, 19 + j, "F" + floorPattern.get(j).getValue() + ": " + tileString);
      }
    }
    System.out.print(playerGrid);

    System.out.print(new GridHorizontalSeparator(50));
  }
}
