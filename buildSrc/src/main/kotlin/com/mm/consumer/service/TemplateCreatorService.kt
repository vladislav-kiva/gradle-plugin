package com.mm.consumer.service

import com.mm.consumer.ModuleExtension

interface TemplateCreatorService {

  fun createTemplate(moduleExtension: ModuleExtension)
}