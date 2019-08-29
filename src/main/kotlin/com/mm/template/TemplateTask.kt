package com.mm.template

import com.mm.template.model.Module
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class TemplateTask : DefaultTask() {

  var moduleVersionMap: Map<Module, String> = emptyMap()

  @TaskAction
  internal fun generate() {

  }
}