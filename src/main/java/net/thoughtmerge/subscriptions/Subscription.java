/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions;

import java.util.Arrays;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventSourcedAggregate;

/**
 *
 * @author evan.gates
 */
public class Subscription extends EventSourcedAggregate {
  
  private String name;
  
  public Subscription() {
  }
  
  public Subscription(Iterable<? extends Event> events) {
    super(events);
  }

  public String getName() {
    return name;
  }
  
  public Iterable<? extends Event> handle(ChangeNameCommand command) {
    this.name = command.name;
    
    return Arrays.asList(new NameChangedEvent(command.name));
  }
  
  public void when(NameChangedEvent event) {
    this.name = event.name;
  }
}
