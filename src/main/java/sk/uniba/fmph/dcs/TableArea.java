package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.StartNewRoundInterface;
import sk.uniba.fmph.dcs.interfaces.TableAreaInterface;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.List;

public class TableArea implements TableAreaInterface {

  private final StartNewRoundInterface bag;
  private final TableCenterInterface tableCenter;
  private final List<TileSourceInterface> factories;

  public TableArea(final StartNewRoundInterface bag, final TableCenterInterface tableCenter, final List<TileSourceInterface> factories) {

    this.bag = bag;
    this.tableCenter = tableCenter;
    this.factories = factories;
  }

  @Override
  public List<Tile> take(final SourcePath sourcePath) {

    if (sourcePath.sourceIndex() == -1) {

      return tableCenter.take(sourcePath.tileIndex());
    }

    return factories.get(sourcePath.sourceIndex()).take(sourcePath.tileIndex());
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
