package sk.uniba.fmph.dcs;

public class Display {
  public static void display(Game game) {

    System.out.print(new GridHorizontalSeparator(50));

    var factories = game.getTableArea().getFactories();
    GridDisplay factoriesGrid = new GridDisplay(50, factories.size(), 5);
    for (int i = 0; i < factories.size(); i++) {

      factoriesGrid.addText(i, 0, "F" + (i + 1) + ":");

      var tiles = factories.get(i).getTiles();

      for (int j = 0; j < tiles.size(); j++) {

        factoriesGrid.addText(i, 1 + j, tiles.get(j).toString());
      }
    }
    System.out.print(factoriesGrid);

    System.out.print(new GridHorizontalSeparator(50));

    GridDisplay tableCenterGrid = new GridDisplay(50, 1, 1);
    tableCenterGrid.addText(0, 0, "Table center: " + Tile.toString(game.getTableArea().getTableCenter().getTiles()));
    System.out.print(tableCenterGrid);

    System.out.print(new GridHorizontalSeparator(50));

    var players = game.getPlayers();
    GridDisplay playerGrid = new GridDisplay(50, 2, 26);
    for (int i = 0; i < players.size(); i++) {

      playerGrid.addText(i, 0, players.get(i).getName() + ":");

      var board = players.get(i).getBoard();

      playerGrid.addText(i, 2, "Points: " + board.getPoints().getValue());

      var patternLines = board.getPatternLines();
      playerGrid.addText(i, 4, "Pattern lines:");
      for (int j = 0; j < patternLines.size(); j++) {

        playerGrid.addText(i, 5 + j, "P" + (j) + ": " + patternLines.get(j).toString());
      }

      var wallLines = board.getWallLines();
      playerGrid.addText(i, 11, "Wall lines:");
      for (int j = 0; j < wallLines.size(); j++) {

        playerGrid.addText(i, 12 + j, "W" + (j) + ": " + wallLines.get(j).toString());
      }

      var floor = board.getFloor();
      var floorPattern = floor.getPointPattern();
      var floorTiles = floor.getTiles();
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
