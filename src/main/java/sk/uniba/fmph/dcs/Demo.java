package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Demo {

  final static ObserverInterface observer = System.out::println;

  // Define the point pattern for the floor line.
  final static List<Points> pointPattern = List.of(
      new Points(-1),
      new Points(-1),
      new Points(-2),
      new Points(-2),
      new Points(-3),
      new Points(-3)
  );

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    //#region Configuration

    System.out.print("Enter the number of players: ");
    int playerCount = Integer.parseInt(scanner.nextLine());

    List<String> playerNames = new ArrayList<>();

    for (int i = 0; i < playerCount; i++) {

      System.out.print("Enter the name of player " + (i + 1) + ": ");

      playerNames.add(scanner.nextLine());
    }

    //#endregion

    //#region Used Tiles

    UsedTilesInterface usedTiles = new UsedTiles();

    //#endregion

    //#region Bag

    BagInterface bag = new Bag(usedTiles);
    bag.fillBag(new Date().hashCode());

    //#endregion

    //#region Table Center

    TableCenterInterface tableCenter = new TableCenter();

    //#endregion

    //#region Factories

    List<TileSourceInterface> factories = new ArrayList<>();

    // Adjust the number of factories based on the player count.
    int factoryCount = playerCount * 2 + 1;

    for (int i = 0; i < factoryCount; i++) {

      factories.add(new Factory(bag, tableCenter));
    }

    //#endregion

    //#region Table Area

    TableAreaInterface tableArea = new TableArea(bag, tableCenter, factories);

    //#endregion

    final List<List<WallLineInterface>> wallLines = new ArrayList<>();
    final List<List<PatternLineInterface>> patternLines = new ArrayList<>();
    final List<FloorInterface> floors = new ArrayList<>();
    final List<PlayerInterface> players = new ArrayList<>();

    for (String playerName : playerNames) {

      //#region Floor

      final FloorInterface floor = new Floor(usedTiles, pointPattern);

      floors.add(floor);

      //#endregion

      final List<WallLineInterface> playerWallLines = new ArrayList<>();
      final List<PatternLineInterface> playerPatternLines = new ArrayList<>();

      TilePermutationIterator iterator = new TilePermutationIterator();

      int i = 0; // Initialize the counter.
      while (iterator.hasNext()) {

        //#region WallLine

        final WallLineInterface newWallLine = new WallLine(iterator.next());

        if (i > 0) { // For all but the first WallLine.

          newWallLine.setLineUp(playerWallLines.get(i - 1));
          playerWallLines.get(i - 1).setLineDown(newWallLine);
        }

        playerWallLines.add(newWallLine);

        //#endregion

        //#region PatternLine

        final PatternLineInterface patternLine = new PatternLine(usedTiles, floor, newWallLine, i + 1);

        playerPatternLines.add(patternLine);

        //#endregion

        i++; // Increment the counter.
      }

      wallLines.add(playerWallLines);
      patternLines.add(playerPatternLines);

      //#region Board

      final BoardInterface board = new Board(floor, new ArrayList<>(playerPatternLines), new ArrayList<>(playerWallLines));

      //#endregion

      //#region Player

      final PlayerInterface player = new Player(playerName, board);

      players.add(player);

      //#endregion
    }

    //region Game

    GameInterface game = new Game(tableArea, players);

    game.getGameObserver().registerObserver(observer);

    game.start();

    //endregion

    //region Gameplay

    while (!game.isGameFinished()) {

      //#region Display the game state.

      Display.display(
          tableCenter,
          new ArrayList<>(factories),
          players,
          patternLines.stream()
              .map(subList -> new ArrayList<TileStateInterface>(subList))
              .collect(Collectors.toList()),
          wallLines.stream()
              .map(subList -> new ArrayList<TileStateInterface>(subList))
              .collect(Collectors.toList()),
          new ArrayList<>(floors)
      );

      //#endregion

      //#region Input

      // Display the name or number of the current player.
      System.out.println(game.onTurn());

      // Subtract 1 from the index to convert from 1-based to 0-based.
      // This is done to make the input more user-friendly.

      System.out.print("Choose source: ");
      int sourceIndex = Integer.parseInt(scanner.nextLine()) - 1;

      System.out.print("Choose tile index: ");
      int tileIndex = Integer.parseInt(scanner.nextLine()) - 1;

      System.out.print("Choose destination: ");
      int destinationIndex = Integer.parseInt(scanner.nextLine()) - 1;

      //#endregion

      game.take(new SourcePath(sourceIndex, tileIndex), destinationIndex);
    }

    //#endregion

    //region Display the game state.

    Display.display(
        tableCenter,
        new ArrayList<>(factories),
        players,
        patternLines.stream()
            .map(subList -> new ArrayList<TileStateInterface>(subList))
            .collect(Collectors.toList()),
        wallLines.stream()
            .map(subList -> new ArrayList<TileStateInterface>(subList))
            .collect(Collectors.toList()),
        new ArrayList<>(floors)
    );

    //#endregion

    scanner.close();
  }
}
