package sk.uniba.fmph.dcs;

import java.util.List;

class Player implements PlayerInterface {

  private String name;
  private final Board board;

  public Player(String name) {

    this.name = name;
    this.board = new Board();
  }

  public static class PlayerState {
    private String name;
    private Board.BoardState boardState;

    private PlayerState(String name, Board board) {

      this.name = name;
      this.boardState = board.saveState();
    }
  }

  public PlayerState saveState() {

    return new PlayerState(name, board);
  }

  public void restoreState(PlayerState state) {

    this.name = state.name;
    this.board.restoreState(state.boardState);
  }

  @Override
  public String getName() {

    return name;
  }

  @Override
  public Board getBoard() {

    return board;
  }

  @Override
  public void put(List<Tile> tiles, int destinationIndex) {

    board.put(tiles, destinationIndex);
  }

  @Override
  public FinishRoundResult finishRound() {

    return board.finishRound();
  }

  @Override
  public void endGame() {

    board.endGame();
  }
}