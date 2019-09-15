package com.mm.consumer.resolver

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException

internal class DirectoryFinderTest {

    @Test
    fun `should go to root and find mm-config folder`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private/")
        System.setProperty("user.dir", module.toString())

        File(testProjectDir.absolutePath.toString() + "/projects").mkdir()
        File(testProjectDir.absolutePath.toString() + "/projects/gateway/").mkdir()
        val mmConfig = File(testProjectDir.absolutePath.toString() + "/projects/mm-config/")
        mmConfig.mkdir()
        module.mkdir()

        val dir = DirectoryFinder().find("mm-config")
        Assertions.assertEquals(testProjectDir.absolutePath.toString() + "\\projects\\mm-config", dir.path)
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `if no folder found throw exception`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        val module = File(testProjectDir.absolutePath.toString() + "/projects/gateway/private/")
        System.setProperty("user.dir", module.toString())

        File(testProjectDir.absolutePath.toString() + "/projects").mkdir()
        File(testProjectDir.absolutePath.toString() + "/projects/gateway/").mkdir()
        module.mkdir()

        Assertions.assertThrows(FileNotFoundException::class.java) {
            DirectoryFinder().find("mm-config")
        }
        System.setProperty("user.dir", userDir)
    }
}