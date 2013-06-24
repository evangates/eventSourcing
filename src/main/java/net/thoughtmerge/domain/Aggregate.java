/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.domain;

/**
 *
 * @author evan.gates
 */
public abstract class Aggregate<IdentifierType> {
  
  private Identifier<IdentifierType> identifier;
  
  public Identifier<IdentifierType> getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(Identifier<IdentifierType> identifier) {
    this.identifier = identifier;
  }
}
