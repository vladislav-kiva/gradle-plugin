package com.mm.consumer.service

import com.mm.consumer.FileComporator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files

internal class IncludeComponentServiceTest {

    @Test
    fun `should insert in settings new module`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private")
        System.setProperty("user.dir", module.toString())
        module.mkdirs()
        val settingsTemplate = javaClass.classLoader.getResource("gradle/not-edited-settings.gradle")
        val settingsGradle = File("${module.parentFile}/settings.gradle")
        Files.copy(settingsTemplate!!.openStream(), settingsGradle.toPath())
        File("${module.parentFile} +/not-edited-settings.gradle").renameTo(settingsGradle)

        val service = IncludeComponentService
        service.injectImport("MX", "/gateway/private-area")

        val expected = javaClass.classLoader.getResource("gradle/edited-settings.gradle")
        Assertions.assertTrue(FileComporator.isFilesEquals(expected!!.path, settingsGradle.path))

        System.setProperty("user.dir", userDir)
    }
}