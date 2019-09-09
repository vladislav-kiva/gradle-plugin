package com.mm.br.gateway.registration.config;

import com.mm.base.config.AppConfigurationConfig;
import com.mm.base.config.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.mm.br.gateway.registration.controller.BrRegistrationController;
import com.mm.br.gateway.registration.config.BrRegistrationJooqConfig;
import com.mm.br.gateway.registration.config.BrRegistrationEventBusConfig;

@Configuration
@Import({
  AppConfigurationConfig.class,
  SwaggerConfig.class,
  BrRegistrationJooqConfig.class,
  BrRegistrationEventBusConfig.class
})
public class BrRegistrationConfig {

  @Bean
  public BrRegistrationController brRegistrationController() {
    return new BrRegistrationController();
  }
}