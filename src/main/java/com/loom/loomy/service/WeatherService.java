package com.loom.loomy.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.loom.loomy.LoomyApplication;
import com.loom.loomy.model.Weather;
import com.loom.loomy.scope.WeatherScope;

@Service
public class WeatherService {
  final Random random = new Random();

  public Weather readWeather() throws InterruptedException, ExecutionException {

    try (var scope = new WeatherScope()) {

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

      scope.joinUntil(Instant.now().plus(1000, LoomyApplication.CHRONO_UNIT));

      return scope.getWeather();
    }
  }
}
