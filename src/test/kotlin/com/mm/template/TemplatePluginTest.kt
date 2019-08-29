package com.mm.template

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TemplatePluginTest {

  @Test
  fun greetingTest() {
    val project = ProjectBuilder.builder().build()
    project.pluginManager.apply("com.mm.template.TemplatePlugin")

    Assertions.assertTrue(project.pluginManager.hasPlugin("com.mm.template.TemplatePlugin"))

    Assertions.assertNotNull(project.tasks.getByName("hello"))
  }

  @Test
  fun `just run it`() {
    TemplateTask().generate()
  }
}