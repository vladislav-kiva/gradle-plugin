package com.mm.consumer.service

import com.mm.consumer.model.Module
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class ConfigCreatorServiceTest {

    private val service = ConfigCreatorService

    @Test
    fun `should create config file wit proper name`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private/")
        System.setProperty("user.dir", module.toString())

        File(testProjectDir.absolutePath.toString() + "/projects").mkdir()
        File(testProjectDir.absolutePath.toString() + "/projects/gateway/").mkdir()
        val mmConfig = File(testProjectDir.absolutePath.toString() + "/projects/mm-config/")
        mmConfig.mkdir()
        module.mkdir()
        service.createConfig(setOf(Module.KOTLIN), "mx", "/gateway/someservice")
        val expected = testProjectDir.absolutePath.toString() + "/projects/mm-config/mx/mx-someservice.yaml"
        Assertions.assertTrue(File(expected).exists())
        System.setProperty("user.dir", userDir)
    }
}