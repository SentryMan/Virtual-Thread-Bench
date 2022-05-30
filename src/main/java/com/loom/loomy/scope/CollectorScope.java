package com.loom.loomy.scope;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;

public class CollectorScope<T> extends StructuredTaskScope<T> {

  protected final Queue<T> results = new ConcurrentLinkedQueue<>();

  protected final Queue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

  @Override
  protected void handleComplete(Future<T> future) {
    switch (future.state()) {
      case RUNNING -> throw new IllegalStateException("Task is still running");
      case SUCCESS -> results.add(future.resultNow());
      case FAILED -> exceptions.add(future.exceptionNow());
      case CANCELLED -> {
        System.out.println("Cancelled");
      }
    }
  }

  public Queue<T> getResults() {
    return results;
  }

  public Queue<Throwable> getExceptions() {
    return exceptions;
  }
}
