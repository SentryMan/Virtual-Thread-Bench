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

@Service
public class WeatherService {
  final Random random = new Random();

  public Weather readWeather() throws InterruptedException, ExecutionException {

    try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Weather>()) {

      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT));
            return new Weather("WA", "Sunny");
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT));
            return new Weather("WB", "Cloudy");
          });
      scope.fork(
          () -> {
            Thread.sleep(Duration.of(random.nextInt(0, 1000), LoomyApplication.CHRONO_UNIT));
            return new Weather("WC", "Partly Cloudy");
          });

      scope.joinUntil(Instant.now().plus(600, LoomyApplication.CHRONO_UNIT));

      // get first result
      return scope.result();

    } catch (final TimeoutException e) {
      return Weather.UNKNOWN;
    }
  }
}
