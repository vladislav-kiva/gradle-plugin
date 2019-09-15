package com.mm.consumer.resolver

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException

internal class FileFinderTest {

    @Test
    fun `should go to root and find mm-config folder`(@TempDir testProjectDir: File) {
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private/")

        File(testProjectDir.absolutePath.toString() + "/projects").mkdir()
        File(testProjectDir.absolutePath.toString() + "/projects/gateway/").mkdir()
        val mmConfig = File(testProjectDir.absolutePath.toString() + "/projects/mm-config/")
        mmConfig.mkdir()
        module.mkdir()

        val dir = FileFinder().find("mm-config", { file -> file.isDirectory }, module.toString())
        Assertions.assertEquals(testProjectDir.absolutePath.toString() + "\\projects\\mm-config", dir.path)
    }

    @Test
    fun `if no folder found throw exception`(@TempDir testProjectDir: File) {
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private/")

        File(testProjectDir.absolutePath.toString() + "/projects").mkdir()
        File(testProjectDir.absolutePath.toString() + "/projects/gateway/").mkdir()
        module.mkdir()

        Assertions.assertThrows(FileNotFoundException::class.java) {
            FileFinder().find("mm-config", { file -> file.isDirectory }, module.toString())
        }
    }
}