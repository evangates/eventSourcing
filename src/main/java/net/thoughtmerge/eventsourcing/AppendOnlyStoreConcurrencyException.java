/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing;

/**
 *
 * @author evan.gates
 */
public class AppendOnlyStoreConcurrencyException extends Exception {

  private int expectedVersion;

  public AppendOnlyStoreConcurrencyException(int expectedVersion) {
    super(String.format("Expected version [%d]", expectedVersion));
    this.expectedVersion = expectedVersion;
  }

  public int getExpectedVersion() {
    return expectedVersion;
  }
}
