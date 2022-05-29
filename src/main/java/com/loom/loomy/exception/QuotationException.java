package com.loom.loomy.exception;

import java.util.Collection;

public class QuotationException extends RuntimeException {

  public QuotationException(String string, Collection<Throwable> exceptions) {
    super(string);

    addSuppresed(exceptions);
  }

  public void addSuppresed(Collection<Throwable> exceptions) {
    exceptions.forEach(this::addSuppressed);
  }
}
