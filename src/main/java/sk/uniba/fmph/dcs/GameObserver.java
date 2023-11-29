package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class GameObserver {
  private final List<ObserverInterface> observers;

  public GameObserver() {

    observers = new ArrayList<>();
  }

  public void registerObserver(final ObserverInterface observer) {

    if (!observers.contains(observer)) {

      observers.add(observer);
    }
  }

  public void cancelObserver(final ObserverInterface observer) {

    observers.remove(observer);
  }

  public void notifyEverybody(final String state) {

    for (ObserverInterface observer : observers) {

      observer.notify(state);
    }
  }
}
