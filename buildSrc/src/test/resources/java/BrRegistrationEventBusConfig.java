package com.mm.br.gateway.registration.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mm.eventbus.EventBus;
import com.mm.eventbus.reactor.ReactorConfiguration;
import com.mm.eventbus.reactor.ReactorEventBus;
import com.mm.eventbus.settings.RabbitSettings;
import java.util.TimeZone;
import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrRegistrationEventBusConfig {

  private final ConfigurationProvider configurationProvider;

  public BrRegistrationEventBusConfig(ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Bean
  public EventBus eventBus() {
    RabbitSettings settings = configurationProvider.bind("rabbit.eventbus", RabbitSettings.class);
    return new ReactorEventBus(ReactorConfiguration(settings), getObjectMapper());
  }

  private ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.setTimeZone(TimeZone.getDefault());
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    return objectMapper;
  }
}