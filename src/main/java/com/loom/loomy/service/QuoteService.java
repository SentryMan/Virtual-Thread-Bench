package com.loom.loomy.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.loom.loomy.LoomyApplication;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.scope.QuotationScope;

@Service
public class QuoteService {

  final Random random = new Random();

  public Quotation readQuotation() throws InterruptedException {

    try (var scope = new QuotationScope()) {

      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency A", random.nextInt(90, 130));
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency B", random.nextInt(90, 120));
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(1, 1000), LoomyApplication.CHRONO_UNIT));
            return new Quotation("Agency C", random.nextInt(100, 110));
          });

      scope.join();
      return scope.bestQuotation();
    }
  }
}
