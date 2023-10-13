package com.loom.loomy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loom.loomy.model.TravelPage;
import com.loom.loomy.service.QuoteService;
import com.loom.loomy.service.WeatherService;

import reactor.core.publisher.Mono;

@RestController
public class Controller {

  private final QuoteService quoter;

  private final WeatherService weatherService;

  public Controller(QuoteService quoter, WeatherService weatherService) {
    this.quoter = quoter;
    this.weatherService = weatherService;
  }

  @GetMapping("/travelpage")
  Mono<TravelPage> travel() throws InterruptedException {
    return Mono.zip(quoter.readQuotation(), weatherService.readWeather(), TravelPage::new);
  }
}
