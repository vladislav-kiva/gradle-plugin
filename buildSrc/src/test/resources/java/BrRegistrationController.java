package com.mm.br.gateway.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrRegistrationController {

  @GetMapping("/hello")
  public String hello() {
    return "TODO FIX ME";
  }
}