package sk.uniba.fmph.dcs;

import java.util.Arrays;

public class GridDisplay {
  private final char[][] grid;
  private final int columnWidth;
  private final int rowCount;
  private final int columnCount;
  private final int totalWidth;

  public GridDisplay(final int totalWidth, final int columnCount, final int rowCount) {

    this.columnCount = columnCount;
    this.rowCount = rowCount;
    this.totalWidth = totalWidth;

    // Compute column width based on total width and column count
    this.columnWidth = (totalWidth - columnCount - 1) / columnCount;

    grid = new char[rowCount][totalWidth];

    initializeGrid();
    drawBorders();
  }

  private void initializeGrid() {

    // Fill grid with spaces.
    for (int i = 0; i < rowCount; i++) {

      Arrays.fill(grid[i], ' ');
    }
  }

  private void drawBorders() {

    // Draw left and right borders.
    for (int i = 0; i < grid.length; i++) {

      grid[i][0] = '|';
      grid[i][grid[i].length - 1] = '|';
    }

    // Draw column separators
    int separatorPosition = columnWidth + 1;
    for (int i = 0; i < grid.length; i++) {

      for (int j = 1; j < columnCount; j++) {

        grid[i][separatorPosition * j] = '|';
      }
    }
  }

  public int getTotalWidth() {

    return totalWidth;
  }

  public void addText(final int column, final int row, String text) {

    if (column < 0 || column >= columnCount || row < 0 || row >= rowCount) {

      throw new IllegalArgumentException("Invalid column or row index");
    }

    if (text.length() > columnWidth) {

      text = text.substring(0, columnWidth);
    }

    int start = column * (columnWidth + 1) + 1;
    for (int i = 0; i < text.length(); i++) {

      grid[row][start + i] = text.charAt(i);
    }
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();

    for (char[] row : grid) {

      for (char ch : row) {

        builder.append(ch);
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}