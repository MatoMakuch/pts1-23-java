package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Tile {
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

  public static Tile fromChar(char value) {

    return charToEnum.getOrDefault(value, null);
  }

  public static Character toChar(Tile tile) {

    return tile.toString().charAt(0);
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

      builder.append(tile.toString());
    }

    return builder.toString();
  }

  @Override
  public String toString() {

    switch (this) {

      case STARTING_PLAYER:

        return "S";

      case RED:

        return "R";

      case GREEN:

        return "G";

      case YELLOW:

        return "Y";

      case BLUE:

        return "B";

      case BLACK:

        return "L";

      default:

        return ".";
    }
  }
}
