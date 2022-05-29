package com.loom.loomy.exception;

import java.util.Collection;

public abstract class AbstractScopeException extends RuntimeException {

  protected AbstractScopeException() {}

  protected AbstractScopeException(String message) {
    super(message);
  }

  protected AbstractScopeException(String message, Throwable cause) {
    super(message, cause);
  }

  protected AbstractScopeException(Throwable cause) {
    super(cause);
  }

  public void addSuppresed(Collection<Throwable> exceptions) {
    exceptions.forEach(this::addSuppressed);
  }
}
