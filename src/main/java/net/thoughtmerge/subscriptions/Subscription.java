/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions;

import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventSourcedAggregate;
import net.thoughtmerge.subscriptions.events.NameChanged;

/**
 *
 * @author evan.gates
 */
public class Subscription extends EventSourcedAggregate {
  
  private String name;
  
  public Subscription(Iterable<? extends Event> events) {
    super(events);
  }

  public String getName() {
    return name;
  }
  
  void when(NameChanged event) {
    this.name = event.name;
  }
}
