/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing;

import net.thoughtmerge.domain.Identifier;

/**
 *
 * @author evan.gates
 */
public class OptimisticConcurrencyException extends Exception {

  private final int serverVersion;
  private final int expectedVersion;
  private final Identifier id;
  private final Iterable<Event> serverEvents;

  public OptimisticConcurrencyException(int serverVersion, int expectedVersion, Identifier id, Iterable<Event> serverEvents) {
    super(String.format("Optimistic Concurrency issue: expected version [%d], found [%d] for id [%s]", expectedVersion, serverVersion, id));
    this.serverVersion = serverVersion;
    this.expectedVersion = expectedVersion;
    this.id = id;
    this.serverEvents = serverEvents;
  }

  public int getServerVersion() {
    return serverVersion;
  }

  public int getExpectedVersion() {
    return expectedVersion;
  }

  public Identifier getId() {
    return id;
  }

  public Iterable<Event> getServerEvents() {
    return serverEvents;
  }
}
