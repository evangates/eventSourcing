/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.domain.services;

import net.thoughtmerge.domain.services.Command;

/**
 *
 * @author evan.gates
 */
public interface ApplicationService {
  void execute(Command command);
}
