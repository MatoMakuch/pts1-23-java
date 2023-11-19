package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  private Game game;

  @BeforeEach
  void setUp() {

    List<String> playerNames = Arrays.asList("Player1", "Player2");

    Bag bag = createBagOf100Tiles();

    game = new Game(playerNames);
  }

  private Bag createBagOf100Tiles() {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 5; i++) {

      builder.append("RRRG");
      builder.append("GYYB");
      builder.append("BLLR");
      builder.append("LGGY");
      builder.append("YBBL");
    }

//    return new Bag(builder.toString());
    return new Bag();
  }

  @Test
  void testPlayerTurns() {

//    assertEquals("Player1", game.onTurn());
//
//    // Player 1.
//    game.take(0, 0, 2);
//
//    // Player 2.
//    game.take(3, 0, 0);
//
//    // Player 1.
//    game.take(1, 1, 1);
//
//    // Player 2.
//    game.take(2, 1, 1);
//
//    // Player 1.
//    game.take(-1, 1, 3);
//
//    // Player 2.
//    game.take(4, 0, 2);
//
//    // Player 1.
//    game.take(-1, 1, 4);
//
//    // Player 2.
//    game.take(-1, 0, 2);
//
//    // Player 1.
//    game.take(-1, 0, 0);
//
//    // Player 2.
//    game.take(-1, 0, 3);
//
//    assertEquals("Player1", game.onTurn());
  }
}
