package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.List;

class Game implements GameInterface {

  public static final String GAME_STARTED_MESSAGE = "Game started";
  public static final String ROUND_STARTED_MESSAGE = "Round started";
  public static final String ROUND_ENDED_MESSAGE = "Round ended";
  public static final String GAME_ENDED_MESSAGE = "Game ended";
  public static final String PLAYER_WON_MESSAGE = "Player X won";

  private final GameObserver observer = new GameObserver();
  private final TableAreaInterface tableArea;
  private final List<PlayerInterface> players;
  private int startingPlayerIndex = 0;
  private int currentPlayerIndex = 0;
  private boolean gameStarted = false;
  private boolean gameFinished = false;

  public Game(TableAreaInterface tableArea, List<PlayerInterface> players) {

    this.tableArea = tableArea;
    this.players = players;
  }

  @Override
  public void start() {

    if (gameStarted) {

      throw new IllegalStateException("Game already started");
    }
    gameStarted = true;

    tableArea.startNewRound();

    observer.notifyEverybody(GAME_STARTED_MESSAGE);
    observer.notifyEverybody(ROUND_STARTED_MESSAGE);
  }

  @Override
  public GameObserver getGameObserver() {

    return observer;
  }

  @Override
  public boolean isGameFinished() {

    return gameFinished;
  }

  @Override
  public String onTurn() {

    return players.get(currentPlayerIndex).getName();
  }

  @Override
  public void take(SourcePath sourcePath, int destinationIndex) {

    if (!gameStarted) {

      throw new IllegalStateException("Game not started");
    }

    handleTileTaking(sourcePath, destinationIndex);

    // Update current player index.
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

    if (tableArea.isRoundEnd()) {

      observer.notifyEverybody(ROUND_ENDED_MESSAGE);

      handleRoundEnd();
    }
  }

  private void handleTileTaking(SourcePath sourcePath, int destinationIndex) {

    final List<Tile> tiles = tableArea.take(sourcePath);

    // Update starting player index.
    if (tiles.contains(Tile.STARTING_PLAYER)) {
      startingPlayerIndex = currentPlayerIndex;
    }

    players.get(currentPlayerIndex).put(tiles, destinationIndex);
  }

  private void handleRoundEnd() {

    var result = finishRound();

    if (result == FinishRoundResult.NORMAL) {

      tableArea.startNewRound();

      observer.notifyEverybody(ROUND_STARTED_MESSAGE);
    }
    else {

      endGame();

      announceWinner();
    }
  }

  private void announceWinner() {

    // Find the player with the most points.
    String winnerName = null;
    Points winnerPoints = new Points(0);

    for (PlayerInterface player : players) {

      if (player.getPoints().getValue() > winnerPoints.getValue()) {

        winnerName = player.getName();
        winnerPoints = player.getPoints();
      }
    }

    // Notify observers.
    observer.notifyEverybody(GAME_ENDED_MESSAGE);

    if (winnerName != null) {

      observer.notifyEverybody(PLAYER_WON_MESSAGE.replace("X", winnerName));
    }
  }

  public FinishRoundResult finishRound() {

    for (PlayerInterface player : players) {

      final FinishRoundResult result = player.finishRound();

      if (result == FinishRoundResult.GAME_FINISHED) {

        gameFinished = true;
      }
    }

    if (gameFinished) {

      return FinishRoundResult.GAME_FINISHED;
    }
    else {

      currentPlayerIndex = startingPlayerIndex;

      return FinishRoundResult.NORMAL;
    }
  }

  public void endGame() {

    for (PlayerInterface player : players) {

      player.endGame();
    }

    gameFinished = true;
  }
}
