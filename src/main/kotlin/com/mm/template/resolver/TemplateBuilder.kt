package com.mm.template.resolver

import com.mm.template.model.Module

interface TemplateBuilder {

  fun build(modules: Map<Module, String>)
}