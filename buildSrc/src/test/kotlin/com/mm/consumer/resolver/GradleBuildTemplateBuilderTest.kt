package com.mm.consumer.resolver

import com.mm.consumer.model.Module
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class GradleBuildTemplateBuilderTest {

    @Test
    fun `should create build gradle`(@TempDir testProjectDir: File) {
        val builder = GradleBuildTemplateBuilder("/gateway/private-area", "mx", FullPathFileOverWriteCreator)
        builder.build(emptyMap())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/build.gradle").exists())
    }

    @Test
    fun `should create build gradle with `(@TempDir testProjectDir: File) {
        val builder = GradleBuildTemplateBuilder("/gateway/private-area", "br", FullPathFileOverWriteCreator)
        builder.build(mapOf(Module.KOTLIN to "", Module.JOOQ to ""))
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/build.gradle").exists())
    }
}