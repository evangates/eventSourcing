/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions.events;

import net.thoughtmerge.eventsourcing.Event;

/**
 *
 * @author evan.gates
 */
public class NameChanged implements Event {
  public final String name;

  public NameChanged(String name) {
    this.name = name;
  }
}
