package com.mm.consumer

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.io.TempDir
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildLogicFunctionalTest {

  @TempDir
  lateinit var testProjectDir: File
  private var buildFile: File? = null

  @BeforeAll
  @Throws(IOException::class)
  fun setup(@TempDir testProjectDir: File) {
    buildFile = File(testProjectDir, "build.gradle")
  }

  @Test
  @Throws(IOException::class)
  fun testHelloWorldTask() {
    val buildFileContent = "task helloWorld {" +
        "    doLast {" +
        "        println 'Hello world!'" +
        "    }" +
        "}"
    writeFile(buildFile, buildFileContent)

    val result = GradleRunner.create()
        .withProjectDir(testProjectDir)
        .withArguments("helloWorld")
        .build()

    Assertions.assertTrue(result.output.contains("Hello world!"))
    Assertions.assertEquals(SUCCESS, result.task(":helloWorld")?.outcome)
  }

  @Throws(IOException::class)
  private fun writeFile(destination: File?, content: String) {
    var output: BufferedWriter? = null
    try {
      output = BufferedWriter(FileWriter(destination!!))
      output.write(content)
    } finally {
      output?.close()
    }
  }
}