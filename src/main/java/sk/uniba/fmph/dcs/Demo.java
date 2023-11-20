package sk.uniba.fmph.dcs;

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

    Game game = new Game(playerNames);
    game.getGameObserver().registerObserver(observer);

    // Simulate a game.
    // Round 1.
    game.take(0, 0, 2);
    game.take(3, 0, 0);
    game.take(1, 1, 1);
    game.take(2, 1, 1);
    game.take(-1, 1, 3);
    game.take(4, 0, 2);
    game.take(-1, 1, 4);
    game.take(-1, 0, 2);
    game.take(-1, 0, 0);
    game.take(-1, 0, 3);

    // Round 2.
    game.take(0, 0, 1);
    game.take(3, 3, 2);
    game.take(1, 1, 0);
    game.take(-1, 1, 4);
    game.take(2, 0, 4);
    game.take(-1, 0, 3);
    game.take(4, 0, -1);
    game.take(-1, 1, 0);
    game.take(-1, 0, 2);
    game.take(-1, 0, -1);

    // Round 3.
    game.take(3, 1, 1);
    game.take(1, 0, 0);
    game.take(2, 0, 0);
    game.take(4, 1, 1);
    game.take(-1, 2, 3);
    game.take(-1, 0, 2);
    game.take(0, 3, 4);
    game.take(-1, 1, 3);
    game.take(-1, 0, -1);

    // Round 4.
    game.take(4, 1, 1);
    game.take(2, 0, 0);
    game.take(0, 0, 2);
    game.take(-1, 2, 1);
    game.take(3, 3, 0);
    game.take(-1, 2, 2);
    game.take(1, 0, 3);
    game.take(-1, 0, 3);
    game.take(-1, 0, 4);
    game.take(-1, 0, -1);
    game.take(-1, 0, -1);

    // Round 5.
    game.take(4, 0, 3);
    game.take(0, 3, 3);
    game.take(1, 0, 1);
    game.take(3, 1, 3);
    game.take(-1, 4, 4);
    game.take(2, 1, 2);
    game.take(-1, 9, 4);
    game.take(-1, 2, 2);
    game.take(-1, 0, -1);
    game.take(-1, 0, 1);

    // Round 6.
    game.take(2, 3, 4);
    game.take(0, 0, 4);
    game.take(1, 0, 1);
    game.take(3, 1, 2);
    game.take(4, 3, 0);
    game.take(-1, 4, 2);
    game.take(-1, 0, 3);
    game.take(-1, 0, -1);
    game.take(-1, 0, 2);

    // Infinite loop for game turns

    Display.display(game);

    while (true) {
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

    // scanner.close();
  }
}
