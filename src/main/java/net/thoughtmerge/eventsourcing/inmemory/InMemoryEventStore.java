/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.inmemory;

import java.util.Collection;
import net.thoughtmerge.domain.Identifier;
import net.thoughtmerge.eventsourcing.AppendOnlyStore;
import net.thoughtmerge.eventsourcing.AppendOnlyStoreConcurrencyException;
import net.thoughtmerge.eventsourcing.DataWithVersion;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventSerializer;
import net.thoughtmerge.eventsourcing.EventStore;
import net.thoughtmerge.eventsourcing.EventStream;
import net.thoughtmerge.eventsourcing.OptimisticConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class InMemoryEventStore implements EventStore {
  private final static Logger logger = LoggerFactory.getLogger(InMemoryEventStore.class);
  
  private final AppendOnlyStore appendOnlyStore;
  private final EventSerializer eventSerializer;

  public InMemoryEventStore(AppendOnlyStore appendOnlyStore, EventSerializer eventSerializer) {
    this.appendOnlyStore = appendOnlyStore;
    this.eventSerializer = eventSerializer;
  }
  
  @Override
  public EventStream loadEventStream(Identifier id) {
    return loadEventStream(id, 0, Integer.MAX_VALUE);
  }

  @Override
  public EventStream loadEventStream(Identifier id, int skipEvents, int maxCount) {
    String name = identifierToString(id);
    Iterable<DataWithVersion> records = appendOnlyStore.readRecords(name, skipEvents, maxCount);
    EventStream stream = new EventStream();
    
    for (DataWithVersion record : records) {
      stream.addEvents(eventSerializer.deserializeEvents(record.getData()));
      stream.setVersion(record.getVersion());
    }
    
    return stream;
  }

  @Override
  public void appendToStream(Identifier id, int expectedVersion, Collection<? extends Event> events) throws OptimisticConcurrencyException {
    if (events.isEmpty()) {
      logger.warn("appendToStream({}, {}, empty events collection)", id, expectedVersion);
      return;
    }
    
    String name = identifierToString(id);
    byte[] data = eventSerializer.serializeEvents(events);
    
    try {
      appendOnlyStore.append(name, data, expectedVersion);
    }
    catch (AppendOnlyStoreConcurrencyException ex) {
      EventStream serverEventStream = loadEventStream(id);
      throw new OptimisticConcurrencyException(serverEventStream.getVersion(), ex.getExpectedVersion(), id, serverEventStream.getEvents());
    }
  }
  
  private String identifierToString(Identifier id) {
    return id.getValue().toString();
  }
}
