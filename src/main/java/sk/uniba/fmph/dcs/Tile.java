package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Tile {
  STARTING_PLAYER,
  RED,
  GREEN,
  YELLOW,
  BLUE,
  BLACK;

  private static final Map<Character, Tile> charToEnum = new HashMap<>();

  static {

    charToEnum.put('S', STARTING_PLAYER);
    charToEnum.put('R', RED);
    charToEnum.put('G', GREEN);
    charToEnum.put('Y', YELLOW);
    charToEnum.put('B', BLUE);
    charToEnum.put('L', BLACK);
  }

  private static Tile fromChar(char value) {

    return charToEnum.getOrDefault(value, null);
  }

  public static List<Tile> fromString(String value) {

    List<Tile> tiles = new ArrayList<>();

    for (char tile : value.toCharArray()) {

      tiles.add(Tile.fromChar(tile));
    }

    return tiles;
  }

  public static String toString(List<Tile> tiles) {

    StringBuilder builder = new StringBuilder();

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
