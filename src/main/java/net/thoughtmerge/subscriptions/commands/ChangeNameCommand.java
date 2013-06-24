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
public class ChangeNameCommand implements Command {
  public final String name;

  public ChangeNameCommand(String name) {
    this.name = name;
  }
}
