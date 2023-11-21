package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {
  public static void main(String[] args) {

    final ObserverInterface observer = System.out::println;

    Scanner scanner = new Scanner(System.in);

//    System.out.print("Enter the number of players: ");
//    int playerCount = Integer.parseInt(scanner.nextLine());

    List<String> playerNames = new ArrayList<>();

//    for (int i = 0; i < playerCount; i++) {
//
//      System.out.print("Enter the name of player " + (i + 1) + ": ");
//
//      playerNames.add(scanner.nextLine());
//    }

    playerNames.add("Player1");
    playerNames.add("Player2");

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 10; i++) {

      builder.append("RRRG");
      builder.append("GYYB");
      builder.append("BLLR");
      builder.append("LGGY");
      builder.append("YBBL");
    }

    Bag.getInstance().fillBag(Tile.fromString(builder.toString()));

    // Define the point pattern for the floor line.
    List<Points> pointPattern = new ArrayList<>();

    pointPattern = new ArrayList<>(
        List.of(
            new Points(-1),
            new Points(-1),
            new Points(-2),
            new Points(-2),
            new Points(-3),
            new Points(-3)
        )
    );

    Game game = new Game(playerNames, pointPattern);
    game.getGameObserver().registerObserver(observer);

    while (!game.isGameOver()) {

      // Display the game state.
      Display.display(game);

      System.out.println(game.onTurn()); // Display the name or number of the current player.

      System.out.print("Choose source: ");
      int sourceIndex = Integer.parseInt(scanner.nextLine());

      System.out.print("Choose tile index: ");
      int tileIndex = Integer.parseInt(scanner.nextLine());

      System.out.print("Choose destination: ");
      int destinationIndex = Integer.parseInt(scanner.nextLine());

      game.take(sourceIndex, tileIndex, destinationIndex);

      Display.display(game);
    }

    scanner.close();
  }
}
