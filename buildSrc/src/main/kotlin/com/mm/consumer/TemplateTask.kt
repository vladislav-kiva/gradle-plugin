package com.mm.consumer

import com.mm.consumer.model.Module
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class TemplateTask : DefaultTask() {

  var moduleVersionMap: Map<Module, String> = emptyMap()

  init {
    group = "idf"
  }

  @TaskAction
  internal fun generate() {
    var extension = project.extensions.findByType(ModuleExtension::class.java)
    if (extension == null) {
      extension = ModuleExtension()
    }
    DefaultTemplateCreatorService.createTemplate(extension)
  }
}