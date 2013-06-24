/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.inmemory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.domain.Identifier;
import net.thoughtmerge.eventsourcing.AppendOnlyStore;
import net.thoughtmerge.eventsourcing.AppendOnlyStoreConcurrencyException;
import net.thoughtmerge.eventsourcing.DataWithVersion;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventSerializer;
import net.thoughtmerge.eventsourcing.OptimisticConcurrencyException;
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
public class InMemoryEventStoreTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  
  private IMocksControl mocksControl;
  
  private AppendOnlyStore appendOnlyStore;
  private EventSerializer eventSerializer;

  private InMemoryEventStore eventStore;

  @Before
  public void setUp() {
    mocksControl = createStrictControl();
    
    appendOnlyStore = mocksControl.createMock(AppendOnlyStore.class);
    eventSerializer = mocksControl.createMock(EventSerializer.class);
    
    eventStore = new InMemoryEventStore(appendOnlyStore, eventSerializer);
    
    assertThat(eventStore).isNotNull();
  }
  
  @Test
  public void appendToStream_withValidEvents_shouldSerializeEvents_andAppendDataWithExpectedVersion() throws Exception {
    // arrange
    final List<TestEvent> events = Arrays.asList(new TestEvent("one"), new TestEvent("two"));
    final Identifier<Integer> identifier = new IntegerIdentifier(1);
    final int expectedVersion = 0;
    
    byte[] data = "hello".getBytes();
    
    expect(eventSerializer.serializeEvents(events))
        .andReturn(data);
    appendOnlyStore.append(identifier.getValue().toString(), data, expectedVersion);
    expectLastCall();
    
    mocksControl.replay();
    
    // act
    eventStore.appendToStream(identifier, expectedVersion, events);
    
    // assert
    mocksControl.verify();
  }
  
  @Test
  public void appendToStream_withAppendOnlyThrowingConcurrencyException_shouldThrowConcurrencyException() throws Exception {
    // arrange
    expectedException.expect(OptimisticConcurrencyException.class);
    
    final List<Event> events = new ArrayList<>();
    events.addAll(Arrays.asList(new TestEvent("one"), new TestEvent("two")));
    final Identifier<Integer> identifier = new IntegerIdentifier(1);
    final int expectedVersion = 0;
    final byte[] data = "hello".getBytes();
    final List<DataWithVersion> dataWithVersions = Arrays.asList(new DataWithVersion(expectedVersion, data));
    
    expect(eventSerializer.serializeEvents(events))
        .andReturn(data);
    appendOnlyStore.append(identifier.getValue().toString(), data, expectedVersion);
    expectLastCall().andThrow(new AppendOnlyStoreConcurrencyException(expectedVersion));
    expect(appendOnlyStore.readRecords(identifier.getValue().toString(), 0, Integer.MAX_VALUE))
        .andReturn(dataWithVersions);
    expect(eventSerializer.deserializeEvents(data))
        .andReturn(events);
    
    mocksControl.replay();
    
    // act
    eventStore.appendToStream(identifier, expectedVersion, events);
    
    // assert
    mocksControl.verify();
  }
  
  private static class TestEvent implements Event {
    private final String value;

    public TestEvent(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
  
  private static class IntegerIdentifier implements Identifier<Integer> {
    private final Integer value;

    public IntegerIdentifier(Integer value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "IntegerIdentifier{" + "value=" + value + '}';
    }

    @Override
    public Integer getValue() {
      return value;
    }
    
    
  }
}