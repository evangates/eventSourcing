/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2013, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */
package net.thoughtmerge.eventsourcing.impl;

import net.thoughtmerge.eventsourcing.EventDispatcher;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.thoughtmerge.domain.Aggregate;
import net.thoughtmerge.eventsourcing.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class CachingReflectionEventDispatcher implements EventDispatcher {

  private final static Logger logger = LoggerFactory.getLogger(CachingReflectionEventDispatcher.class);
  
  @Override
  public void dispatch(Aggregate target, Event event) {
    final Class eventType = event.getClass();
    
    try {
      if (!methodCache.containsKey(eventType)) {
        methodCache.put(eventType, findHandlerMethod(target, event));
      }
      
      methodCache.get(eventType).invoke(target, event);
    }
    catch (UnsupportedOperationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      logger.error("Could not invoke event handler method: ", ex);
      throw new UnsupportedOperationException("Could not invoke event handler method", ex);
    }
  }

  private Method findHandlerMethod(Aggregate target, Event event) {
    Method[] methods = target.getClass().getDeclaredMethods();
    
    for (Method method : methods) {
      Class<?>[] parameterTypes = method.getParameterTypes();
      
      if (parameterTypes.length == 1 && parameterTypes[0].equals(event.getClass())) {
        return method;
      }
    }
    
    throw new UnsupportedOperationException("Could not find method to handle event: " + event.getClass().getName());
  }
  
  private final static Map<Class, Method> methodCache = new HashMap<>();
}
