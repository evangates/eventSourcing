/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions.commands;

import net.thoughtmerge.eventsourcing.Command;

/**
 *
 * @author evan.gates
 */
public class ChangeName implements Command {
  public final String name;

  public ChangeName(String name) {
    this.name = name;
  }
}
