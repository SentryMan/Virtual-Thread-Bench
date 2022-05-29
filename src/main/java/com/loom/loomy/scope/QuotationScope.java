package com.loom.loomy.scope;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import com.loom.loomy.model.Quotation;
import com.loom.loomy.model.Quotation.QuotationException;

import jdk.incubator.concurrent.StructuredTaskScope;

public class QuotationScope extends StructuredTaskScope<Quotation> {
  private final Collection<Quotation> quotations = new ConcurrentLinkedQueue<>();

  private final Collection<Throwable> exceptions = new ConcurrentLinkedQueue<>();

  @Override
  protected void handleComplete(Future<Quotation> future) {
    switch (future.state()) {
      case RUNNING -> throw new IllegalStateException("Task is still running");
      case SUCCESS -> quotations.add(future.resultNow());
      case FAILED -> exceptions.add(future.exceptionNow());
      case CANCELLED -> {
        System.out.println("Cancelled");
      }
    }
  }

  public QuotationException exceptions() {
    final var exception = new QuotationException();
    exceptions.forEach(exception::addSuppressed);
    return exception;
  }

  public Quotation bestQuotation() {
    return quotations.stream()
        .min(Comparator.comparing(Quotation::quotation))
        .orElseThrow(this::exceptions);
  }
}
