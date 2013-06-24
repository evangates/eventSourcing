/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions;

import net.thoughtmerge.domain.Identifier;
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

  @Override
  public Identifier getIdentifier() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public String getName() {
    return name;
  }
  
  void when(NameChanged event) {
    this.name = event.name;
  }
}
