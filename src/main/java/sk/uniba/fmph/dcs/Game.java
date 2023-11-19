package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game implements GameInterface {
  private final Map<String, Player> players;
  private final TableArea tableArea;
  private final Bag bag;
  private final GameObserver gameObserver;
  private int currentPlayerIndex = 0;

  public Game(List<String> playerNames) {

    this.bag = new Bag();
    this.tableArea = new TableArea(playerNames.size(), bag);
    this.gameObserver = new GameObserver();

    // Define the point pattern for the floor line
    List<Points> pointPattern = new ArrayList<>();
    pointPattern.add(new Points(-1)); // The starting tile always goes into the floor line
    pointPattern.add(new Points(-1)); // Typically, the first few tiles have the same negative points
    pointPattern.add(new Points(-2));
    pointPattern.add(new Points(-2)); // and so on, up to your game's specific rules
    pointPattern.add(new Points(-2));
    pointPattern.add(new Points(-3));
    pointPattern.add(new Points(-3));

    this.players = new HashMap<>();
    for (String playerName : playerNames) {

      players.put(
          playerName,
          new Player(
              playerName,
              new Board(
                  new Floor(
                      new UsedTiles(),
                      pointPattern
                  )
              )
          )
      );
    }
  }

  @Override
  public void take(String playerName, int sourceIndex, int tileIndex, int destinationIndex) {

    final List<Tile> tiles = tableArea.take(sourceIndex, tileIndex);

    players.get(playerName).put(tiles, destinationIndex);
  }

  public FinishRoundResult finishRound() {

    boolean isGameFinished = false;

    for (Player player : players.values()) {

      final FinishRoundResult result = player.finishRound();

      if (result == FinishRoundResult.GAME_FINISHED) {

        isGameFinished = true;
      }
    }

    if (isGameFinished) {

      return FinishRoundResult.GAME_FINISHED;
    }
    else {

      return FinishRoundResult.NORMAL;
    }
  }

  public void endGame() {

    for (Player player : players.values()) {

      player.endGame();
    }
  }

  public String state() {

    StringBuilder builder = new StringBuilder();

    for (Player player : players.values()) {

      builder.append("Player ");
      builder.append(player.getName());
      builder.append(": ");
      builder.append(player.getState());
    }

    builder.append(tableArea.state());

    return builder.toString();
  }

  private String getFinalScores() {
    // Implement logic to retrieve final scores from players.
    return ""; // Placeholder return
  }

  public void startNewRound() {
    // Implement logic to start a new round, including refilling factories from the bag.
  }

  public void nextPlayerTurn() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    // Notify observers about the change of turn
    gameObserver.notifyEverybody("Player " + currentPlayerIndex + "'s turn.");
  }

  // Additional methods as needed...
}
