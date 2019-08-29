package com.mm.template.resolver

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class GradleBuildTemplateBuilderTest {

  @Test
  fun `should create build gradle`(@TempDir testProjectDir: File) {
    val builder = GradleBuildTemplateBuilder(testProjectDir.absolutePath, FullPathFileOverWriteCreator)
    builder.build(emptyMap())
  }
}