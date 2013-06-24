/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.thoughtmerge.domain.Aggregate;
import net.thoughtmerge.eventsourcing.Command;
import net.thoughtmerge.eventsourcing.CommandDispatcher;
import net.thoughtmerge.eventsourcing.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class CachingReflectionCommandDispatcher implements CommandDispatcher {

  private final static Logger logger = LoggerFactory.getLogger(CachingReflectionCommandDispatcher.class);
  
  @Override
  public Iterable<Event> dispatch(Aggregate target, Command command) {
    final Class commandType = command.getClass();
    
    try {
      if (!methodCache.containsKey(commandType)) {
        methodCache.put(commandType, findHandlerMethod(target, command));
      }
      
      return (Iterable<Event>)methodCache.get(commandType).invoke(target, command);
    }
    catch (UnsupportedOperationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      logger.error("Could not invoke event handler method: ", ex);
      throw new UnsupportedOperationException("Could not invoke event handler method", ex);
    }
  }

  private Method findHandlerMethod(Aggregate target, Command command) {
    Method[] methods = target.getClass().getDeclaredMethods();
    
    for (Method method : methods) {
      Class<?>[] parameterTypes = method.getParameterTypes();
      
      if (parameterTypes.length == 1 && parameterTypes[0].equals(command.getClass())) {
        return method;
      }
    }
    
    throw new UnsupportedOperationException("Could not find method to handle command: " + command.getClass().getName());
  }
  
  private final static Map<Class, Method> methodCache = new HashMap<>();
}
