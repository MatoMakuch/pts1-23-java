package sk.uniba.fmph.dcs;

public class Display {
  public static void display(Game game) {

    System.out.print(new GridHorizontalSeparator(50));

    var factories = game.getTableArea().getFactories();
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
    tableCenterGrid.addText(0, 0, "Table center: " + game.getTableArea().getTableCenter().getState());
    System.out.print(tableCenterGrid);

    System.out.print(new GridHorizontalSeparator(50));

    var players = game.getPlayers();
    GridDisplay playerGrid = new GridDisplay(50, 2, 26);
    for (int i = 0; i < players.size(); i++) {

      playerGrid.addText(i, 0, players.get(i).getName() + ":");

      var board = players.get(i).getBoard();

      playerGrid.addText(i, 2, "Points: " + board.getPoints().getValue());

      var patternLineStates = board.getPatternLineStates();
      playerGrid.addText(i, 4, "Pattern lines:");
      for (int j = 0; j < patternLineStates.size(); j++) {

        playerGrid.addText(i, 5 + j, "P" + (j) + ": " + patternLineStates.get(j));
      }

      var wallLines = board.getWallLineStates();
      playerGrid.addText(i, 11, "Wall lines:");
      for (int j = 0; j < wallLines.size(); j++) {

        playerGrid.addText(i, 12 + j, "W" + (j) + ": " + wallLines.get(j));
      }

      var floor = board.getFloor();
      var floorPattern = floor.getPointPattern();
      var floorTiles = Tile.fromString(floor.getState());
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
