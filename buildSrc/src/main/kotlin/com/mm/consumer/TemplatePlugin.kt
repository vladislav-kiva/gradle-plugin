package com.mm.consumer

import org.gradle.api.Plugin
import org.gradle.api.Project

//todo lazy loading
open class TemplatePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    project.extensions.create("templateSettings", ModuleExtension::class.java)
    project.tasks.create("generateTemplate", TemplateTask::class.java) { task ->
      task.moduleVersionMap = emptyMap()
    }
  }
}