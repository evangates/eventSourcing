/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.subscriptions;

import net.thoughtmerge.eventsourcing.Event;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author evan.gates
 */
public class NameChangedEvent implements Event {

  public final String name;

  public NameChangedEvent(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(3, 89)
        .append(name)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }

    final NameChangedEvent other = (NameChangedEvent) obj;
    return new EqualsBuilder()
        .append(name, other.name)
        .isEquals();
  }
}
