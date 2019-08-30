package com.mm.consumer.resolver

import com.mm.consumer.model.Module

interface TemplateBuilder {

  fun build(modules: Map<Module, String>)
}