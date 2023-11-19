package sk.uniba.fmph.dcs;

import java.util.Collections;

public class GridHorizontalSeparator {
  private final int totalWidth;

  public GridHorizontalSeparator(int totalWidth) {

    this.totalWidth = totalWidth;
  }

  @Override
  public String toString() {

    // Create a horizontal separator line with vertical borders on each side.
    return "|" + String.join("", Collections.nCopies(totalWidth - 2, "-")) + "|\n";
  }
}
