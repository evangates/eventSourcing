/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.eventsourcing.Event;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
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
public class DefaultObjectSerializerTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  private IMocksControl mocks;
  private DefaultObjectSerializer serializer;

  @Before
  public void setUp() {
    mocks = createStrictControl();

    serializer = new DefaultObjectSerializer();
  }

  @Test
  public void serializeEvents_withSingleEvent_canBeDeserialized() throws Exception {
    // arrange
    List<TestEvent> events = Arrays.asList(new TestEvent("value"));

    // act
    byte[] serialized = serializer.serializeEvents(events);
    Iterable<Event> result = serializer.deserializeEvents(serialized);

    // assert
    assertThat(result).containsOnly(events.toArray());
  }
  
  @Test
  public void serializeEvents_withMultipleEvents_canAllBeDeserialized() throws Exception {
    // arrange
    List<TestEvent> events = Arrays.asList(new TestEvent("one"), new TestEvent("two"));

    // act
    byte[] serialized = serializer.serializeEvents(events);
    Iterable<Event> result = serializer.deserializeEvents(serialized);

    // assert
    assertThat(result).containsOnly(events.toArray());
  }
  
  private static class TestEvent implements Event {

    public final String value;

    public TestEvent(String value) {
      this.value = value;
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
    }
  }
}