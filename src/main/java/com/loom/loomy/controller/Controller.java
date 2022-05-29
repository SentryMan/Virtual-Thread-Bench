package com.loom.loomy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loom.loomy.model.TravelPage;
import com.loom.loomy.scope.TravelPageScope;
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
    TravelPage travelPage;
    try (var scope = new TravelPageScope()) {

      scope.fork(weatherService::readWeather);
      scope.fork(quoter::readQuotation);

      scope.join();

      travelPage = scope.travelPage();
    } catch (final InterruptedException e) {
      e.printStackTrace();
      throw e;
    }

    return travelPage;
  }
}
