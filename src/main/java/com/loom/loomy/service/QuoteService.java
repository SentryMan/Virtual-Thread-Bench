package com.loom.loomy.service;

import java.time.Duration;
import java.util.Comparator;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.loom.loomy.LoomyApplication;
import com.loom.loomy.exception.QuotationException;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.scope.CollectorScope;

@Service
public class QuoteService {

  final Random random = new Random();

  public Quotation readQuotation() throws InterruptedException {

    try (var scope = new CollectorScope<Quotation>()) {

      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency A", random.nextInt(1, 1000));
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency B", random.nextInt(1, 1000));
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency C", random.nextInt(1, 1000));
          });

      scope.join();

      return scope.getResults().stream()
          .min(Comparator.comparing(Quotation::quotation))
          .orElseThrow(() -> new QuotationException("failed to get quotes", scope.getExceptions()));
    }
  }
}
