package com.loom.loomy;

import java.time.temporal.ChronoUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import com.loom.loomy.config.LoomThreadPool;

@SpringBootApplication
public class LoomyApplication {
  public static final ChronoUnit CHRONO_UNIT = ChronoUnit.MILLIS;

  public static void main(String[] args) {

    new SpringApplicationBuilder(LoomyApplication.class).run(args);
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> jettyPoolCustomizer() {
    return server -> server.setThreadPool(new LoomThreadPool());
  }
}
