package com.mm.consumer

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TemplatePluginTest {

  @Test
  fun `template plugin task should exist`() {
    val project = ProjectBuilder.builder().build()
    project.plugins.apply("com.mm.template.plugin")
    Assertions.assertTrue(project.tasks.getByName("generateTemplate") is TemplateTask)
  }
}