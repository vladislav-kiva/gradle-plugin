package com.mm.br.gateway.registration.config;

import com.mm.platform.jooq.config.DefaultJooqBuilder;
import com.mm.platform.jooq.config.JooqSettings;
import org.cfg4j.provider.ConfigurationProvider;
import org.jooq.DSLContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BrRegistrationJooqConfig {

  private final JooqSettings configObject;

  public BrRegistrationJooqConfig(ConfigurationProvider configurationProvider) {
    this.configObject = configurationProvider.bind("moneyman.jooq", JooqSettings.class);
  }

  @Bean(name = "MONEYMAN_JOOQ")
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public DSLContext dslContext() {
    return DefaultJooqBuilder
        .of(configObject)
        .withDefaultProviders()
        .build();
  }
}