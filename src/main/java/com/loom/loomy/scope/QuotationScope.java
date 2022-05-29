package com.loom.loomy.scope;

import java.util.Comparator;

import com.loom.loomy.exception.QuotationException;
import com.loom.loomy.model.Quotation;

public class QuotationScope extends GenericStructuredTaskScope<Quotation> {

  public Quotation bestQuotation() {
    return results.stream()
        .min(Comparator.comparing(Quotation::quotation))
        .orElseThrow(() -> new QuotationException("failed to get quotes", exceptions));
  }
}
