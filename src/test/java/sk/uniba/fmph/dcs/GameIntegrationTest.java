package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameIntegrationTest {

  private UsedTilesInterface usedTiles;
  private BagInterface bag;
  private TableCenterInterface tableCenter;
  private List<TileSourceInterface> factories = new ArrayList<>();
  private TableAreaInterface tableArea;
  private List<List<WallLineInterface>> wallLines = new ArrayList<>();
  private List<List<PatternLineInterface>> patternLines = new ArrayList<>();
  private List<FloorInterface> floors = new ArrayList<>();
  private List<BoardInterface> boards = new ArrayList<>();
  private List<PlayerInterface> players = new ArrayList<>();
  private GameInterface game;
  private ObserverInterface observer;
  private List<String> observerOutput = new ArrayList<>();

  @BeforeEach
  void setUp() {

    //#region Clear

    factories.clear();
    wallLines.clear();
    patternLines.clear();
    floors.clear();
    boards.clear();
    players.clear();
    observerOutput.clear();

    //#endregion

    //#region Configuration

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 6; i++) {

      builder.append("RRRG");
      builder.append("GYYB");
      builder.append("BLLR");
      builder.append("LGGY");
      builder.append("YBBL");
    }

    String bagState = builder.toString();

    int playerCount = 2;
    List<String> playerNames = Arrays.asList("Player1", "Player2");
    List<Points> pointPattern = List.of(
        new Points(-1),
        new Points(-1),
        new Points(-2),
        new Points(-2),
        new Points(-3),
        new Points(-3)
    );

    //#endregion

    //#region Used Tiles

    usedTiles = new UsedTiles();

    //#endregion

    //#region Bag

    bag = new Bag(usedTiles);
    bag.setState(bagState);

    //#endregion

    //#region Table Center

    tableCenter = new TableCenter();

    //#endregion

    //#region Factories

    // Adjust the number of factories based on the player count.
    int factoryCount = playerCount * 2 + 1;

    for (int i = 0; i < factoryCount; i++) {

      factories.add(new Factory(bag, tableCenter));
    }

    //#endregion

    //#region Table Area

    tableArea = new TableArea(bag, tableCenter, factories);

    //#endregion

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

      boards.add(board);

      //#endregion

      //#region Player

      final PlayerInterface player = new Player(playerName, board);

      players.add(player);

      //#endregion
    }

    //#region Observer

    observer = state -> observerOutput.add(state);

    //#endregion

    //region Game

    game = new Game(tableArea, players);

    game.getGameObserver().registerObserver(observer);

    //endregion
  }

  @Test
  void testPlayerTurns() {

    assertEquals("Player1", game.onTurn());

    //#region First round simulation

    // Player 1.
    game.take(0, 0, 2);

    // Player 2.
    game.take(3, 0, 0);

    // Player 1.
    game.take(1, 1, 1);

    // Player 2.
    game.take(2, 1, 1);

    // Player 1 takes from the table center first.
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

    //#endregion

    assertEquals("Player1", game.onTurn());
    assertEquals(4, players.get(0).getPoints().getValue());
    assertEquals(2, players.get(1).getPoints().getValue());

    //#region Second round simulation

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

    //#endregion

    assertEquals("Player2", game.onTurn());
    assertEquals(5, players.get(0).getPoints().getValue());
    assertEquals(3, players.get(1).getPoints().getValue());

    //#region Third round simulation

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

    //#endregion

    assertEquals("Player2", game.onTurn());
    assertEquals(26, players.get(0).getPoints().getValue());
    assertEquals(10, players.get(1).getPoints().getValue());

    //#region Fourth round simulation

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

    //#endregion

    assertEquals("Player1", game.onTurn());
    assertEquals(40, players.get(0).getPoints().getValue());
    assertEquals(19, players.get(1).getPoints().getValue());

    //#region Fifth round simulation

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

    //#endregion

    assertEquals("Player1", game.onTurn());
    assertEquals(38, players.get(0).getPoints().getValue());
    assertEquals(32, players.get(1).getPoints().getValue());

    //#region Sixth round simulation

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

    //#endregion

    assertEquals(100, players.get(0).getPoints().getValue());
    assertEquals(31, players.get(1).getPoints().getValue());
  }
}
