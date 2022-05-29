package com.loom.loomy.scope;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;

public class GenericStructuredTaskScope<T> extends StructuredTaskScope<T> {

  protected final Collection<T> results = new ConcurrentLinkedQueue<>();

  protected final Collection<Throwable> exceptions = new ConcurrentLinkedQueue<>();

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

  public Collection<T> getResults() {
    return results;
  }

  public Collection<Throwable> getExceptions() {
    return exceptions;
  }
}
