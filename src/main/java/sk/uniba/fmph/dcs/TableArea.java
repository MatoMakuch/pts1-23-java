package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class TableArea {
  private List<TileSource> factories;
  private TableCenter tableCenter;

  public TableArea(int playerCount, Bag bag) {

    this.factories = new ArrayList<>();
    this.tableCenter = new TableCenter();

    // Adjust the number of factories based on the player count.
    int factoryCount = playerCount * 2 + 1;

    for (int i = 0; i < factoryCount; i++) {

      factories.add(new Factory(bag, tableCenter));
    }
  }

  public TableCenter getTableCenter() {

    return tableCenter;
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

  public String state() {

    StringBuilder builder = new StringBuilder();

    for (TileSource factory : factories) {

      builder.append("Factory:");
      builder.append(factory.state());

      builder.append("\n");
    }

    builder.append("TableCenter:");
    builder.append(tableCenter.state());

    return builder.toString();
  }
}