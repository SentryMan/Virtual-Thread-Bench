package com.loom.loomy.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.loom.loomy.LoomyApplication;
import com.loom.loomy.model.Weather;

import reactor.core.publisher.Mono;

@Service
public class WeatherService {
  final Random random = new Random();

  public Mono<Weather> readWeather() {
    return Mono.firstWithSignal(
        Mono.just(new Weather("WA", "Sunny"))
            .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)),
        Mono.just(new Weather("WB", "Cloudy"))
            .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)),
        Mono.just(new Weather("WC", "Partly Cloudy"))
            .delayElement(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT)));
  }
}
