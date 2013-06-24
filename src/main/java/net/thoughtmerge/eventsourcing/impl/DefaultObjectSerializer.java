/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import net.thoughtmerge.eventsourcing.Event;
import net.thoughtmerge.eventsourcing.EventSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class DefaultObjectSerializer implements EventSerializer {
  
  private final static Logger logger = LoggerFactory.getLogger(DefaultObjectSerializer.class);

  @Override
  public byte[] serializeEvents(Iterable<? extends Event> events) {
    ByteArrayOutputStream stream = null;
    ObjectOutputStream objectStream = null;
    
    try {
      stream = new ByteArrayOutputStream();
      objectStream = new ObjectOutputStream(stream);
      
      for (Event event : events) {
        objectStream.writeObject(event);
      }
    
      byte[] retval = stream.toByteArray();
      
      return retval;
    }
    catch (IOException ex) {
      logger.error("Error serializing events:", ex);
      return null;
    }
    finally {
      IOUtils.closeQuietly(objectStream);
      IOUtils.closeQuietly(stream);
    }
  }

  @Override
  public Iterable<Event> deserializeEvents(byte[] data) {
    ByteArrayInputStream stream = null;
    ObjectInputStream objectStream = null;
    
    try {
      stream = new ByteArrayInputStream(data);
      objectStream = new ObjectInputStream(stream);
      
      Iterable<Event> retval = (Iterable<Event>)objectStream.readObject();
      
      return retval;
    }
    catch (ClassNotFoundException | IOException ex) {
      logger.error("Error deserializing event(s):", ex);
      return Collections.emptyList();
    }
    finally {
      IOUtils.closeQuietly(objectStream);
      IOUtils.closeQuietly(stream);
    }
  }
  
}
