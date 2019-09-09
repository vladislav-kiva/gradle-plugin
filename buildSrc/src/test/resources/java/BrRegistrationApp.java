package com.mm.br.gateway.registration;

import com.mm.base.endpoint.MMApp;
import com.mm.br.gateway.registration.config.BrRegistrationConfig;

public class BrRegistrationApp {

  public static void main(String[] args) {
    MMApp.run("br", "br-registration", BrRegistrationConfig.class);
  }

}