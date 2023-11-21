package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.List;

public class TableArea {

  private Bag bag;
  private final TableCenter tableCenter;
  private final List<TileSourceInterface> factories;

  public TableArea(int playerCount) {

    this.tableCenter = new TableCenter();
    this.factories = new ArrayList<>();

    // Adjust the number of factories based on the player count.
    int factoryCount = playerCount * 2 + 1;

    for (int i = 0; i < factoryCount; i++) {

      factories.add(new Factory(tableCenter));
    }
  }

  //#region Memento

  public static class TableAreaState {
    private String tableCenterState;
    private List<String> factoryStates;

    private TableAreaState(TableCenterInterface tableCenter, List<TileSourceInterface> factories) {

      this.tableCenterState = tableCenter.getState();

      this.factoryStates = new ArrayList<>();

      for (TileSourceInterface factory : factories) {

        this.factoryStates.add(factory.getState());
      }
    }
  }

  public TableAreaState saveState() {

    return new TableAreaState(tableCenter, factories);
  }

  public void restoreState(TableAreaState state) {

    this.tableCenter.setState(state.tableCenterState);

    for (int i = 0; i < factories.size(); i++) {

      factories.get(i).setState(state.factoryStates.get(i));
    }
  }

  //#endregion

  public TableCenter getTableCenter() {

    return tableCenter;
  }

  public List<TileSourceInterface> getFactories() {

    return factories;
  }

  public List<Tile> take(int sourceIndex, int tileIndex) {

    if (sourceIndex == -1) {

      return tableCenter.take(tileIndex);
    }
    return factories.get(sourceIndex).take(tileIndex);
  }

  public boolean isRoundEnd() {

    for (TileSourceInterface factory : factories) {

      if (!factory.isEmpty()) {

        return false;
      }
    }
    return tableCenter.isEmpty();
  }

  public void startNewRound() {

    // Refill factories with tiles from the bag.
    for (TileSourceInterface factory : factories) {

      factory.startNewRound();
    }
    tableCenter.startNewRound();
  }
}