package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.Collections;
import java.util.List;

class Game implements GameInterface {
  private final GameObserver observer = new GameObserver();
  private final TableAreaInterface tableArea;
  private final List<PlayerInterface> players;
  private int startingPlayerIndex = 0;
  private int currentPlayerIndex = 0;
  private boolean isGameOver = false;

  public Game(TableAreaInterface tableArea, List<PlayerInterface> players) {

    this.tableArea = tableArea;
    this.players = players;

    tableArea.startNewRound();
  }

  @Override
  public GameObserver getGameObserver() {

    return observer;
  }

  @Override
  public boolean isGameOver() {

    return isGameOver;
  }

  @Override
  public String onTurn() {

    return players.get(currentPlayerIndex).getName();
  }

  @Override
  public void take(int sourceIndex, int tileIndex, int destinationIndex) {

    handleTileTaking(sourceIndex, tileIndex, destinationIndex);

    // Update current player index.
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

    if (tableArea.isRoundEnd()) {

      observer.notifyEverybody("Round ended.");

      handleRoundEnd();
    }
  }

  private void handleTileTaking(int sourceIndex, int tileIndex, int destinationIndex) {

    final List<Tile> tiles = tableArea.take(sourceIndex, tileIndex);

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

      observer.notifyEverybody("Round started.");
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
    observer.notifyEverybody("Game ended.");

    if (winnerName != null) {

      observer.notifyEverybody("Player " + winnerName + " won with " + winnerPoints.getValue() + " points.");
    }
  }

  public FinishRoundResult finishRound() {

    boolean isGameFinished = false;

    for (PlayerInterface player : players) {

      final FinishRoundResult result = player.finishRound();

      if (result == FinishRoundResult.GAME_FINISHED) {

        isGameFinished = true;
      }
    }

    if (isGameFinished) {

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

    isGameOver = true;
  }
}
