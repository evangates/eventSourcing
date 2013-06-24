package net.thoughtmerge.eventsourcing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author evan.gates
 */
public class EventStream {

  private int version;
  
  private List<Event> events;

  public EventStream() {
    events = new ArrayList<>();
  }

  public int getVersion() {
    return version;
  }
  
  public void setVersion(int version) {
    this.version = version;
  }

  public Iterable<Event> getEvents() {
    return Collections.unmodifiableCollection(events);
  }
  
  public void addEvents(Iterable<? extends Event> events) {
    for (Event event : events) {
      this.events.add(event);
    }
  }
}
