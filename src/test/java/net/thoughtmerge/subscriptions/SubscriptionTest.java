/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.subscriptions;

import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.eventsourcing.Event;
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
    
    subscription = new Subscription();
  }
  
  @Test
  public void constructor_withEvents_shouldDispatchTheEvents() throws Exception {
    // arrange
    final NameChangedEvent nameChangedEvent = new NameChangedEvent("foobar");
    
    mocksControl.replay();
    
    // act
    Subscription result = new Subscription(Arrays.asList(nameChangedEvent));

    // assert
    mocksControl.verify();
    
    assertThat(result.getName()).isEqualTo("foobar");
  }
  
  @Test
  public void handle_ChangeNameCommand_shouldReturnNameChangedEvent() throws Exception {
    // arrange
    final String NEW_NAME = "new name";
    
    final ChangeNameCommand changeNameCommand = new ChangeNameCommand(NEW_NAME);
    final List<? extends Event> expectedEvents = Arrays.asList(new NameChangedEvent(NEW_NAME));
    // act
    Iterable<? extends Event> result = subscription.handle(changeNameCommand);
    
    // assert
    assertThat(result).containsOnly(expectedEvents.toArray());
  }
}
