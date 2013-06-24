/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.subscriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventDispatcher;
import net.thoughtmerge.subscriptions.events.NameChanged;
import org.easymock.Capture;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.easymock.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 *
 * @author evan.gates
 */
public class SubscriptionTest {
  
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  private IMocksControl mocksControl;
  
  private Subscription subscription;

  @Before
  public void setUp() {
    mocksControl = createStrictControl();
  }
  
  @Test
  public void constructor_withEvents_shouldDispatchTheEvents() throws Exception {
    // arrange
    final NameChanged nameChangedEvent = new NameChanged("foobar");
    
    mocksControl.replay();
    
    // act
    subscription = new Subscription(Arrays.asList(nameChangedEvent));

    // assert
    mocksControl.verify();
    
    assertThat(subscription.getName()).isEqualTo("foobar");
  }

}