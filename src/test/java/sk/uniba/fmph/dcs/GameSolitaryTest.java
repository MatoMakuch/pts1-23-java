package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import sk.uniba.fmph.dcs.fake.FakePlayer;
import sk.uniba.fmph.dcs.fake.FakeTableArea;
import sk.uniba.fmph.dcs.interfaces.PlayerInterface;

import java.util.*;

public class GameSolitaryTest {
  private Game game;
  private FakePlayer player1, player2;
  private FakeTableArea tableArea;

  @Before
  public void setUp() {

    player1 = new FakePlayer("Alice");
    player2 = new FakePlayer("Bob");

    // Initialize the table area with different sets of tiles.
    Map<SourcePath, List<Tile>> initialTiles = new HashMap<>();

    List<Tile> round1Tiles = Tile.fromString("RGB");

    initialTiles.put(new SourcePath(0, 0), round1Tiles);

    tableArea = new FakeTableArea(initialTiles);

    List<PlayerInterface> players = Arrays.asList(player1, player2);

    game = new Game(tableArea, players);
    game.start();
  }

  @Test
  public void testIsGameOver_Initial() {

    assertFalse(game.isGameFinished());
  }

  @Test
  public void testIsGameOver_AfterEnd() {

    game.endGame();

    assertTrue(game.isGameFinished());
  }

  @Test
  public void testOnTurn_Initial() {

    assertEquals("Alice", game.onTurn());
  }

  @Test
  public void testOnTurn_AfterAction() {

    game.take(new SourcePath(0, 0), 0);

    assertEquals("Bob", game.onTurn());
  }

  @Test
  public void testStartingPlayerTile() {

    // Initialize the table area with a starting player tile.
    Map<SourcePath, List<Tile>> initialTiles = new HashMap<>();

    List<Tile> startingTiles = Tile.fromString("S");

    initialTiles.put(new SourcePath(0, 0), startingTiles);

    tableArea = new FakeTableArea(initialTiles);

    game = new Game(tableArea, Arrays.asList(player1, player2));
    game.start();

    // Player takes the starting player tile.
    game.take(new SourcePath(0, 0), 0);

    assertTrue(player1.getPutTiles().get(0).contains(Tile.STARTING_PLAYER)); // Check if the starting player tile is taken
  }

  @Test
  public void testTileTaking_MultipleRounds() {

    // Initialize the table area with different sets of tiles.
    Map<SourcePath, List<Tile>> initialTiles = new HashMap<>();

    List<Tile> round1Tiles = Tile.fromString("RGB");
    List<Tile> round2Tiles = Tile.fromString("YBL");

    initialTiles.put(new SourcePath(0, 0), round1Tiles);
    initialTiles.put(new SourcePath(1, 0), round2Tiles);

    tableArea = new FakeTableArea(initialTiles);

    game = new Game(tableArea, Arrays.asList(player1, player2));
    game.start();

    // Simulate two rounds of tile taking.
    game.take(new SourcePath(0, 0), 0); // First round.
    game.take(new SourcePath(1, 0), 1); // Second round.

    assertEquals(round1Tiles, player1.getPutTiles().get(0));
    assertEquals(round2Tiles, player2.getPutTiles().get(1));
  }

  @Test
  public void testTake_RoundEnd() {

    tableArea.setRoundEnd(true); // Simulate end of round.

    game.take(new SourcePath(0, 0), 0); // Simulate taking a tile.

    assertEquals(1, player1.getRoundsPlayed());
    assertEquals("Alice", game.onTurn()); // Turn should not change as round ended.
  }

  @Test
  public void testGetGameObserver_NonNull() {

    assertNotNull(game.getGameObserver());
  }

  @Test
  public void testFinishRound_Results() {

    player1.setFinishRoundResult(FinishRoundResult.NORMAL);
    player2.setFinishRoundResult(FinishRoundResult.GAME_FINISHED);

    game.finishRound();
    assertTrue(game.isGameFinished()); // Game should end as one player finished.
  }

  @Test
  public void testPlayer_GameOverState() {

    game.endGame();

    assertTrue(player1.isGameOver());
    assertTrue(player2.isGameOver());
  }

  @Test
  public void testRoundEnd() {

    this.tableArea.setRoundEnd(true);

    game.take(new SourcePath(0, 0), 0);

    assertEquals(1, player1.getRoundsPlayed());
  }

  @Test
  public void testTilePlacement() {

    // Testing if tiles are correctly placed by players.
    List<Tile> tilesToPut = Tile.fromString("RR"); // Create sample tiles.

    game.take(new SourcePath(0, 0), 1); // Simulate a turn.

    assertTrue(player1.getPutTiles().get(1).containsAll(tilesToPut));
  }

  @Test
  public void testTake_WithoutStartingGame_ShouldThrowException() {

    // Reinitialize the game without calling start().
    game = new Game(tableArea, Arrays.asList(player1, player2));

    assertThrows(IllegalStateException.class, () -> {

      // This should throw an IllegalStateException as the game has not been started.
      game.take(new SourcePath(0, 0), 0);
    });
  }

  @Test
  public void testStartingGame_Twice_ShouldThrowException() {

    assertThrows(IllegalStateException.class, () -> {

      // The game is already started once in setUp().
      // Attempting to start it again should throw an exception.
      game.start();
    });
  }
}
