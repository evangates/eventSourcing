/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing;

import net.thoughtmerge.domain.Aggregate;

/**
 *
 * @author evan.gates
 */
public interface EventHandler<AggregateType extends Aggregate> {
  void handle(AggregateType aggregate, Event event);
}
