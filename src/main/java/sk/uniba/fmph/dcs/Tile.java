package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Tile {
  STARTING_PLAYER, RED, GREEN, YELLOW, BLUE, BLACK;

  private static final Map<Character, Tile> CHARACTER_TILE_MAP = new HashMap<>();

  static {

    CHARACTER_TILE_MAP.put('S', STARTING_PLAYER);
    CHARACTER_TILE_MAP.put('R', RED);
    CHARACTER_TILE_MAP.put('G', GREEN);
    CHARACTER_TILE_MAP.put('Y', YELLOW);
    CHARACTER_TILE_MAP.put('B', BLUE);
    CHARACTER_TILE_MAP.put('L', BLACK);
  }

  private static Tile fromChar(final char value) {

    return CHARACTER_TILE_MAP.getOrDefault(value, null);
  }

  public static List<Tile> fromString(final String value) {

    final List<Tile> tiles = new ArrayList<>();

    for (char tile : value.toCharArray()) {

      tiles.add(Tile.fromChar(tile));
    }

    return tiles;
  }

  public static String toString(final List<Tile> tiles) {

    final StringBuilder builder = new StringBuilder();

    for (Tile tile : tiles) {

      if (tile == null) {

        builder.append(".");
        continue;
      }

      builder.append(tile);
    }

    return builder.toString();
  }

  @Override
  public String toString() {

    return switch (this) {
      case STARTING_PLAYER -> "S";
      case RED -> "R";
      case GREEN -> "G";
      case YELLOW -> "Y";
      case BLUE -> "B";
      case BLACK -> "L";
    };
  }
}
