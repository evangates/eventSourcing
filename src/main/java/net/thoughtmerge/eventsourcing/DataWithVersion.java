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
public class DataWithVersion {
  private int version;
  private byte[] data;

  public DataWithVersion(int version, byte[] data) {
    this.version = version;
    this.data = data;
  }

  public int getVersion() {
    return version;
  }

  public byte[] getData() {
    return data;
  }
}
