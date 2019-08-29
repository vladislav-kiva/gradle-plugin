package com.mm.template

import org.gradle.api.Plugin
import org.gradle.api.Project
//todo lazy loading
class TemplatePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    project.tasks.create("hello", TemplateTask::class.java) { task ->
      task.moduleVersionMap = emptyMap()
    }
  }
}