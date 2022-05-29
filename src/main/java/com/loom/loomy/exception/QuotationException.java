package com.loom.loomy.exception;

import java.util.Collection;

public class QuotationException extends AbstractScopeException {

  public QuotationException(String string, Collection<Throwable> exceptions) {
    super(string);

    addSuppresed(exceptions);
  }
}
