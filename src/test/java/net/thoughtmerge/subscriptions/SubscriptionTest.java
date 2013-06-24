/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.subscriptions;

import java.util.Arrays;
import net.thoughtmerge.subscriptions.commands.ChangeNameCommand;
import net.thoughtmerge.subscriptions.events.NameChangedEvent;
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
    final NameChangedEvent nameChangedEvent = new NameChangedEvent("foobar");
    
    mocksControl.replay();
    
    // act
    subscription = new Subscription(Arrays.asList(nameChangedEvent));

    // assert
    mocksControl.verify();
    
    assertThat(subscription.getName()).isEqualTo("foobar");
  }

}