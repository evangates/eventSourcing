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
public interface AppendOnlyStore {
  void append(String name, byte[] data, int expectedVersion) throws AppendOnlyStoreConcurrencyException;
  void append(String name, byte[] data);
  
  Iterable<DataWithVersion> readRecords(String name, int afterVersion, int maxCount);
  Iterable<DataWithName> readRecords(int afterVersion, int maxCount);
  
}
