package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class TableArea {

  private Bag bag;
  private TableCenter tableCenter;
  private List<TileSource> factories;

  public TableArea(int playerCount) {

    this.tableCenter = new TableCenter();
    this.factories = new ArrayList<>();

    // Adjust the number of factories based on the player count.
    int factoryCount = playerCount * 2 + 1;

    for (int i = 0; i < factoryCount; i++) {

      factories.add(new Factory(tableCenter));
    }
  }

  public static class TableAreaState {
    private TileSource.TileSourceState tableCenterState;
    private List<TileSource.TileSourceState> factoryStates;
  }

  public TableAreaState saveState() {

    TableAreaState state = new TableAreaState();

    state.tableCenterState = tableCenter.saveState();

    state.factoryStates = new ArrayList<>();

    for (TileSource factory : factories) {

      state.factoryStates.add(factory.saveState());
    }

    return state;
  }

  public void restoreState(TableAreaState state) {

    this.tableCenter.restoreState(state.tableCenterState);

    for (int i = 0; i < factories.size(); i++) {

      factories.get(i).restoreState(state.factoryStates.get(i));
    }
  }

  public TableCenter getTableCenter() {

    return tableCenter;
  }

  public List<TileSource> getFactories() {

    return factories;
  }

  public List<Tile> take(int sourceIndex, int tileIndex) {

    if (sourceIndex == -1) {

      return tableCenter.take(tileIndex);
    }
    return factories.get(sourceIndex).take(tileIndex);
  }

  public boolean isRoundEnd() {

    for (TileSource factory : factories) {

      if (!factory.isEmpty()) {

        return false;
      }
    }
    return tableCenter.isEmpty();
  }

  public void startNewRound() {

    // Refill factories with tiles from the bag.
    for (TileSource factory : factories) {

      factory.startNewRound();
    }
    tableCenter.startNewRound();
  }
}