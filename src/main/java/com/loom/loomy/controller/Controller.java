package com.loom.loomy.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loom.loomy.exception.TravelPageException;
import com.loom.loomy.model.TravelPage;
import com.loom.loomy.service.QuoteService;
import com.loom.loomy.service.WeatherService;

import jdk.incubator.concurrent.StructuredTaskScope;

@RestController
public class Controller {

  private final QuoteService quoter;

  private final WeatherService weatherService;

  public Controller(QuoteService quoter, WeatherService weatherService) {
    this.quoter = quoter;
    this.weatherService = weatherService;
  }

  @GetMapping("/travelpage")
  TravelPage travel() throws InterruptedException {

    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

      final var weatherFork = scope.fork(weatherService::readWeather);
      final var quoteFork = scope.fork(quoter::readQuotation);

      scope.join().throwIfFailed();

      return new TravelPage(quoteFork.resultNow(), weatherFork.resultNow());

    } catch (final InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new TravelPageException("Failed to get travel info", e);
    }
  }
}
