package com.loom.loomy;

import java.time.temporal.ChronoUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LoomyApplication {

  public static final ChronoUnit CHRONO_UNIT = ChronoUnit.MILLIS;

  public static void main(String[] args) {

    new SpringApplicationBuilder(LoomyApplication.class).run(args);
  }
}
