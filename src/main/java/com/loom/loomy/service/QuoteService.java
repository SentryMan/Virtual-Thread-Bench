package com.loom.loomy.service;

import java.time.Duration;
import java.util.Comparator;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.loom.loomy.LoomyApplication;
import com.loom.loomy.exception.QuotationException;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.model.Weather;
import com.loom.loomy.scope.CollectorScope;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuoteService {

  final Random random = new Random();

  public Mono<Quotation> readQuotation() throws InterruptedException {
    return Flux.merge(
            Mono.just(new Quotation("Agency A", random.nextInt(1, 1000)))
                .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)),
            Mono.just(new Quotation("Agency B", random.nextInt(1, 1000)))
                .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)),
            Mono.just(new Quotation("Agency C", random.nextInt(1, 1000)))
                .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)))
        .sort(Comparator.comparing(Quotation::quotation))
        .next();
  }
}
