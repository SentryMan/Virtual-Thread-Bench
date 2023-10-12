package com.loom.loomy.scope;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope;

public class CollectorScope<T> extends StructuredTaskScope<T> {

  protected final Queue<T> results = new ConcurrentLinkedQueue<>();

  protected final Queue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

  @Override
  protected void handleComplete(Subtask<? extends T> subtask) {
    switch (subtask.state()) {
      case UNAVAILABLE -> throw new IllegalStateException("Task is still running");
      case SUCCESS -> results.add(subtask.get());
      case FAILED -> exceptions.add(subtask.exception());
    }
  }

  public Queue<T> getResults() {
    return results;
  }

  public Queue<Throwable> getExceptions() {
    return exceptions;
  }
}
