package sk.uniba.fmph.dcs;

public final class Points {
  private int value;

  public Points(final int value) {
    this.value = value;
  }

  public static Points sum(final Iterable<Points> pts) {

    int sum = 0;
    for (final Points points : pts) {

      sum += points.getValue();
    }
    return new Points(sum);
  }

  public int getValue() {
    return value;
  }

  public void setValue(final int value) {

    this.value = value;
  }

  public void add(final Points points) {

    value += points.getValue();
  }

  @Override
  public int hashCode() {

    final int prime = 31;

    int result = 1;
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {

    if (this == obj) {

      return true;
    }

    if (obj == null) {

      return false;
    }
    if (getClass() != obj.getClass()) {

      return false;
    }

    final Points other = (Points) obj;

    return value == other.value;
  }

  @Override
  public String toString() {
    return "Points[value=" + value + "]";
  }
}