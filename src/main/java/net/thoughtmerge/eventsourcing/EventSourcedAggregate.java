/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing;

import java.util.ArrayList;
import java.util.List;
import net.thoughtmerge.domain.Aggregate;
import net.thoughtmerge.eventsourcing.impl.CachingReflectionEventDispatcher;

/**
 *
 * @author evan.gates
 */
public abstract class EventSourcedAggregate extends Aggregate {
  
  private final List<Event> changes;
  
  private final static EventDispatcher dispatcher = new CachingReflectionEventDispatcher();
  
  protected EventSourcedAggregate() {
    this(null);
  }
  
  protected EventSourcedAggregate(Iterable<? extends Event> events) {
    
    this.changes = new ArrayList<>();
    
    this.handle(events);
  }
  
  protected final void handle(Iterable<? extends Event> events) {
    for (Event event : events) {
      EventSourcedAggregate.dispatcher.dispatch(this, event);
      this.changes.add(event);
    }
  }
}
