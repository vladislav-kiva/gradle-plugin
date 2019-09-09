package com.mm.br.gateway.registration

import com.mm.base.endpoint.MMApp
import com.mm.br.gateway.registration.config.BrRegistrationConfig

object BrRegistrationApp {

  @JvmStatic
  fun main(args: Array<String>) {
    MMApp.run("br", "br-registration", BrRegistrationConfig::class.java)
  }
}