package sk.uniba.fmph.dcs.fake;

import sk.uniba.fmph.dcs.FinishRoundResult;
import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.interfaces.PlayerInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakePlayer implements PlayerInterface {

  private final String name;
  private final Map<Integer, List<Tile>> putTiles = new HashMap<>();
  private int roundsPlayed = 0;
  private FinishRoundResult finishRoundResult;

  public FakePlayer(final String name) {

    this.name = name;
  }

  public Map<Integer, List<Tile>> getPutTiles() {

    return putTiles;
  }

  public int getRoundsPlayed() {

    return roundsPlayed;
  }

  public void setFinishRoundResult(final FinishRoundResult finishRoundResult) {

    this.finishRoundResult = finishRoundResult;
  }

  public boolean isGameOver() {

    return true;
  }

  @Override
  public Points getPoints() {

    return new Points(roundsPlayed * 10);
  }

  @Override
  public void put(final List<Tile> tiles, final int destinationIndex) {

    putTiles.put(destinationIndex, tiles);
  }

  @Override
  public FinishRoundResult finishRound() {

    roundsPlayed++;

    return finishRoundResult;
  }

  @Override
  public void endGame() {

  }

  @Override
  public String getName() {

    return name;
  }
}
