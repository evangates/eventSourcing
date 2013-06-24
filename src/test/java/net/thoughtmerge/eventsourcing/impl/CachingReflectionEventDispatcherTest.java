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
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.easymock.EasyMock.*;

/**
 *
 * @author evan.gates
 */
public class CachingReflectionEventDispatcherTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  private IMocksControl mocks;
  private CachingReflectionEventDispatcher dispatcher;

  @Before
  public void setUp() {
    mocks = createStrictControl();

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
