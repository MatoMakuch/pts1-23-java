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

    createBagOf100Tiles();

    List<String> playerNames = Arrays.asList("Player1", "Player2");

    game = new Game(playerNames,
      List.of(
        new Points(-1),
        new Points(-1),
        new Points(-2),
        new Points(-2),
        new Points(-3),
        new Points(-3)
      )
    );
  }

  private void createBagOf100Tiles() {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 6; i++) {

      builder.append("RRRG");
      builder.append("GYYB");
      builder.append("BLLR");
      builder.append("LGGY");
      builder.append("YBBL");
    }

    Bag.getInstance().fillBag(Tile.fromString(builder.toString()));
  }

  @Test
  void testPlayerTurns() {

    assertEquals("Player1", game.onTurn());

    // Player 1.
    game.take(0, 0, 2);

    // Player 2.
    game.take(3, 0, 0);

    // Player 1.
    game.take(1, 1, 1);

    // Player 2.
    game.take(2, 1, 1);

    // Player 1.
    game.take(-1, 1, 3);

    // Player 2.
    game.take(4, 0, 2);

    // Player 1.
    game.take(-1, 1, 4);

    // Player 2.
    game.take(-1, 0, 2);

    // Player 1.
    game.take(-1, 0, 0);

    // Player 2.
    game.take(-1, 0, 3);

    assertEquals("Player1", game.onTurn());
    assertEquals(4, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(2, game.getPlayers().get(1).getBoard().getPoints().getValue());

    // Player 1.
    game.take(0, 0, 1);

    // Player 2.
    game.take(3, 3, 2);

    // Player 1.
    game.take(1, 1, 0);

    // Player 2 takes from the table center first.
    game.take(-1, 1, 4);

    // Player 1.
    game.take(2, 0, 4);

    // Player 2.
    game.take(-1, 0, 3);

    // Player 1.
    game.take(4, 0, -1);

    // Player 2.
    game.take(-1, 1, 0);

    // Player 1.
    game.take(-1, 0, 2);

    // Player 2.
    game.take(-1, 0, -1);

    assertEquals("Player2", game.onTurn());
    assertEquals(5, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(3, game.getPlayers().get(1).getBoard().getPoints().getValue());

    // Player 2.
    game.take(3, 1, 1);

    // Player 1.
    game.take(1, 0, 0);

    // Player 2.
    game.take(2, 0, 0);

    // Player 1.
    game.take(4, 1, 1);

    // Player 2 takes from the table center first.
    game.take(-1, 2, 3);

    // Player 1.
    game.take(-1, 0, 2);

    // Player 2.
    game.take(0, 3, 4);

    // Player 1.
    game.take(-1, 1, 3);

    // Player 2.
    game.take(-1, 0, -1);

    assertEquals("Player2", game.onTurn());
    assertEquals(26, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(10, game.getPlayers().get(1).getBoard().getPoints().getValue());

    // Player 2.
    game.take(4, 1, 1);

    // Player 1.
    game.take(2, 0, 0);

    // Player 2.
    game.take(0, 0, 2);

    // Player 1 takes from the table center first.
    game.take(-1, 2, 1);

    // Player 2.
    game.take(3, 3, 0);

    // Player 1.
    game.take(-1, 2, 2);

    // Player 2.
    game.take(1, 0, 3);

    // Player 1.
    game.take(-1, 0, 3);

    // Player 2.
    game.take(-1, 0, 4);

    // Player 1.
    game.take(-1, 0, -1);

    // Player 2.
    game.take(-1, 0, -1);

    assertEquals("Player1", game.onTurn());
    assertEquals(40, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(19, game.getPlayers().get(1).getBoard().getPoints().getValue());

    // Player 1.
    game.take(4, 0, 3);

    // Player 2.
    game.take(0, 3, 3);

    // Player 1.
    game.take(1, 0, 1);

    // Player 2.
    game.take(3, 1, 3);

    // Player 1 takes from the table center first.
    game.take(-1, 4, 4);

    // Player 2.
    game.take(2, 1, 2);

    // Player 1.
    game.take(-1, 9, 4);

    // Player 2.
    game.take(-1, 2, 2);

    // Player 1.
    game.take(-1, 0, -1);

    // Player 2.
    game.take(-1, 0, 1);

    assertEquals("Player1", game.onTurn());
    assertEquals(38, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(32, game.getPlayers().get(1).getBoard().getPoints().getValue());

    // Player 1.
    game.take(2, 3, 4);

    // Player 2.
    game.take(0, 0, 4);

    // Player 1.
    game.take(1, 0, 1);

    // Player 2.
    game.take(3, 1, 2);

    // Player 1.
    game.take(4, 3, 0);

    // Player 2 takes from the table center first.
    game.take(-1, 4, 2);

    // Player 1.
    game.take(-1, 0, 3);

    // Player 2.
    game.take(-1, 0, -1);

    // Player 1.
    game.take(-1, 0, 2);

    assertEquals(100, game.getPlayers().get(0).getBoard().getPoints().getValue());
    assertEquals(31, game.getPlayers().get(1).getBoard().getPoints().getValue());
  }
}
