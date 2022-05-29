package com.loom.loomy.scope;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.loom.loomy.model.Weather;

import jdk.incubator.concurrent.StructuredTaskScope;

// scope for getting the first result of same type
public class WeatherScope implements AutoCloseable {

  private final StructuredTaskScope.ShutdownOnSuccess<Weather> scope =
      new StructuredTaskScope.ShutdownOnSuccess<>();

  private boolean timeout = false;

  public WeatherScope joinUntil(Instant deadline) throws InterruptedException {
    try {
      scope.joinUntil(deadline);
    } catch (final TimeoutException e) {
      scope.shutdown();
      timeout = true;
    }
    return this;
  }

  public Future<Weather> fork(Callable<? extends Weather> task) {
    return scope.fork(task);
  }

  @Override
  public void close() {
    scope.close();
  }

  public Weather getWeather() throws ExecutionException {
    if (!timeout) return scope.result();
    return Weather.UNKNOWN;
  }
}
