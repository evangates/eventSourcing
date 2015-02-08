/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.impl;

import net.thoughtmerge.domain.Aggregate;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.test.junit.TestNamePrinter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author evan.gates
 */
public class CachingReflectionEventDispatcherTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  
  @Rule
  public TestNamePrinter testNamePrinter = new TestNamePrinter();
  
  private CachingReflectionEventDispatcher dispatcher;

  @Before
  public void setUp() {
    dispatcher = new CachingReflectionEventDispatcher();
  }

  @Test
  public void dispatch_withNoHandler_shouldThrowUnsupportedOperationException() throws Exception {
    // arrange
    expectedException.expect(UnsupportedOperationException.class);

    TestAggregate aggregate = new TestAggregate();
    TestEvent event = new TestEvent();

    // act
    dispatcher.dispatch(aggregate, event);
  }

  private class TestAggregate extends Aggregate<Integer> {
  }

  private class TestEvent implements Event {
  }
}
