package com.loom.loomy.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loom.loomy.exception.TravelPageException;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.model.TravelPage;
import com.loom.loomy.model.Weather;
import com.loom.loomy.service.QuoteService;
import com.loom.loomy.service.WeatherService;

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

    try (ShutdownOnFailure scope = new StructuredTaskScope.ShutdownOnFailure()) {

      final Subtask<Weather> weatherFork = scope.fork(weatherService::readWeather);
      final Subtask<Quotation> quoteFork = scope.fork(quoter::readQuotation);

      scope.join().throwIfFailed();

      return new TravelPage(quoteFork.get(), weatherFork.get());

    } catch (final InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new TravelPageException("Failed to get travel info", e);
    }

  }
}
