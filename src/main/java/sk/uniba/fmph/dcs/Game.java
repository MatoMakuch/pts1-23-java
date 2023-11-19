package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game implements GameInterface {
  private final TableArea tableArea;
  private final List<Player> players;
  private int startingPlayerIndex = 0;
  private int currentPlayerIndex = 0;

  public Game(List<String> playerNames) {

    this.tableArea = new TableArea(playerNames.size());
    this.players = new ArrayList<>();

    for (String playerName : playerNames) {

      players.add(new Player(playerName));
    }
  }

  public static class GameState {
    private final TableArea.TableAreaState tableAreaState;
    private final List<Player.PlayerState> playerStates;
    private final int currentPlayerIndex;
    private final int startingPlayerIndex;

    private GameState(TableArea tableArea, List<Player> players, int currentPlayerIndex, int startingPlayerIndex) {

      this.tableAreaState = tableArea.saveState();

      this.playerStates = new ArrayList<>();
      for (Player player : players) {

        this.playerStates.add(player.saveState());
      }

      this.currentPlayerIndex = currentPlayerIndex;
      this.startingPlayerIndex = startingPlayerIndex;
    }
  }

  public GameState saveState() {

    return new GameState(tableArea, players, currentPlayerIndex, startingPlayerIndex);
  }

  public void restoreState(GameState state) {

    this.tableArea.restoreState(state.tableAreaState);

    for (int i = 0; i < players.size(); i++) {

      Player.PlayerState playerState = state.playerStates.get(i);
      players.get(i).restoreState(playerState);
    }

    this.currentPlayerIndex = state.currentPlayerIndex;
    this.startingPlayerIndex = state.startingPlayerIndex;
  }


  @Override
  public String onTurn() {

    return players.get(currentPlayerIndex).getName();
  }

  @Override
  public void take(int sourceIndex, int tileIndex, int destinationIndex) {

    final List<Tile> tiles = tableArea.take(sourceIndex, tileIndex);

    if (tiles.contains(Tile.STARTING_PLAYER)) {

       startingPlayerIndex = currentPlayerIndex;
    }

    players.get(currentPlayerIndex).put(tiles, destinationIndex);

    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

    if (!tableArea.isRoundEnd()) {

      return;
    }

    var result = finishRound();

    if (result == FinishRoundResult.NORMAL) {

      return;
    }

    endGame();
  }

  public FinishRoundResult finishRound() {

    boolean isGameFinished = false;

    for (Player player : players) {

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

    for (Player player : players) {

      player.endGame();
    }
  }
}
