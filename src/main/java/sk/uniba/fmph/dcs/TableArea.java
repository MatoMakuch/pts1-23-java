package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.StartNewRoundInterface;
import sk.uniba.fmph.dcs.interfaces.TableAreaInterface;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.List;

public class TableArea implements TableAreaInterface {

  private final StartNewRoundInterface bag;
  private final TableCenterInterface tableCenter;
  private final List<TileSourceInterface> factories;

  public TableArea(StartNewRoundInterface bag, TableCenterInterface tableCenter, List<TileSourceInterface> factories) {

    this.bag = bag;
    this.tableCenter = tableCenter;
    this.factories = factories;
  }

  @Override
  public List<Tile> take(int sourceIndex, int tileIndex) {

    if (sourceIndex == -1) {

      return tableCenter.take(tileIndex);
    }

    return factories.get(sourceIndex).take(tileIndex);
  }

  @Override
  public boolean isRoundEnd() {

    for (TileSourceInterface factory : factories) {

      if (!factory.isEmpty()) {

        return false;
      }
    }

    return tableCenter.isEmpty();
  }

  @Override
  public void startNewRound() {

    bag.startNewRound();

    // Refill factories with tiles from the bag.
    for (TileSourceInterface factory : factories) {

      factory.startNewRound();
    }
    tableCenter.startNewRound();
  }
}