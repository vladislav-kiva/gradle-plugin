package com.mm.template.resolver

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileWriter

internal class FullPathFileOverWriteCreatorTest {

  private val pathCreator = FullPathFileOverWriteCreator

  @Test
  fun `should create root dirs and file`(@TempDir testProjectDir: File) {
    val path = testProjectDir.toPath().toString() + "/test1/test2/build.gradle"
    val file = File(path)
    pathCreator.createFileWithDirectories(file)
    Assertions.assertTrue(file.exists())
  }

  @Test
  fun `should overwrite file if exists`(@TempDir testProjectDir: File) {
    val path = testProjectDir.toPath().toString() + "/test1/test2/build.gradle"
    val file = File(path)
    pathCreator.createFileWithDirectories(file)
    val fr = FileWriter(file, true)
    fr.write("data")
    fr.close()
    pathCreator.createFileWithDirectories(file)
    Assertions.assertEquals(0, file.length())
    Assertions.assertTrue(file.exists())
  }
}