package sk.uniba.fmph.dcs;

import java.util.List;

class Player implements PlayerInterface {

  private String name;
  private Board board;

  public Player(String name, Board board) {

    this.name = name;
    this.board = board;
  }

  @Override
  public String getName() {

    return name;
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

  @Override
  public String getState() {

    return name + ":\n" + board.state();
  }
}