package net.thoughtmerge.eventsourcing;

import net.thoughtmerge.domain.Identifier;
import java.util.Collection;

/**
 *
 * @author evan.gates
 */
public interface EventStore {

  EventStream loadEventStream(Identifier id);

  EventStream loadEventStream(Identifier id, int skipEvents, int maxCount);

  void appendToStream(Identifier id, int expectedVersion, Collection<? extends Event> events) throws OptimisticConcurrencyException;
}
