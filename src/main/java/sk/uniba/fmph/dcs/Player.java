package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BoardInterface;
import sk.uniba.fmph.dcs.interfaces.PlayerInterface;

import java.util.List;

record Player(String name, BoardInterface board) implements PlayerInterface {

  @Override
  public String getName() {

    return name;
  }

  @Override
  public Points getPoints() {

    return board.getPoints();
  }

  @Override
  public void put(final List<Tile> tiles, final int destinationIndex) {

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