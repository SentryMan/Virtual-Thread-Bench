package com.loom.loomy.exception;

import java.util.Collection;

public class TravelPageException extends AbstractScopeException {

  public TravelPageException(String string, Collection<Throwable> exceptions) {
    super(string);

    addSuppresed(exceptions);
  }
}
