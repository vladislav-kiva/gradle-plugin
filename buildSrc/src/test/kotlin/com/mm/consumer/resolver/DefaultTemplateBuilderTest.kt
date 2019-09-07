package com.mm.consumer.resolver

import com.google.common.base.CaseFormat
import com.mm.consumer.DefaultTemplateBuilder
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

internal class DefaultTemplateBuilderTest {

    @Test
    fun `should create build gradle file with kotlin`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        createBuildGradleWithModules(setOf(Module.KOTLIN))
        val generatedFile = File(testProjectDir.absolutePath + "/gateway/private-area/build.gradle")
        val expected = javaClass.classLoader.getResource("gradle/just-kotlin-build.gradle")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(
            isFilesEquals(
                expected!!.path,
                testProjectDir.absolutePath + "/gateway/private-area/build.gradle"
            )
        )
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create build gradle with all kotlin extensions`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        createBuildGradleWithModules(
            setOf(
                Module.KOTLIN,
                Module.JOOQ,
                Module.MONGO,
                Module.RETROFIT,
                Module.RABBIT,
                Module.FEIGN
            )
        )
        val generatedFile = File(testProjectDir.absolutePath + "/gateway/private-area/build.gradle")
        val expected = javaClass.classLoader.getResource("gradle/all-kotlin-build.gradle")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(
            isFilesEquals(
                expected!!.path,
                testProjectDir.absolutePath + "/gateway/private-area/build.gradle"
            )
        )
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmapp kt file`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val countryCamel = country[0].toUpperCase() + country.substring(1).toLowerCase()
        val modulePath = "/gateway/private-area"
        val appCamel = if (modulePath.contains("/")) {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
        } else {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
        }
        val genFileName = "/src/kotlin/com/mm$modulePath/$countryCamel${appCamel}App.kt"
        val template = "templates/kotlin/MMAppTemplate.kt.template"
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(setOf(Module.KOTLIN, Module.JOOQ), genFileName, template, null)
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/BrPrivateAreaApp.kt").exists())
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmApp mmConfig rabbitConfig and mmController`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val countryCamel = country[0].toUpperCase() + country.substring(1).toLowerCase()
        val modulePath = "/gateway/private-area"
        val appCamel = if (modulePath.contains("/")) {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
        } else {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
        }
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)

        var genFileName = "/src/kotlin/com/mm$modulePath/$countryCamel${appCamel}App.kt"
        var template = "templates/kotlin/MMAppTemplate.kt.template"

        builder.build(setOf(Module.KOTLIN, Module.JOOQ, Module.RABBIT), genFileName, template, null)

        genFileName = "/src/kotlin/com/mm$modulePath/config/$countryCamel${appCamel}Config.kt"
        template = "templates/kotlin/Config.kt.template"

        builder.build(setOf(Module.KOTLIN), genFileName, template, "templates/labels/kotlin/config.kt.json")

        genFileName = "/src/kotlin/com/mm$modulePath/controller/$countryCamel${appCamel}Controller.kt"
        template = "templates/kotlin/MMController.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)


        genFileName = "/src/kotlin/com/mm$modulePath/config/$countryCamel${appCamel}RabbitConfig.kt"
        template = "templates/kotlin/Rabbit.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/config/BrPrivateAreaRabbitConfig.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/controller/BrPrivateAreaController.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/config/BrPrivateAreaConfig.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/BrPrivateAreaApp.kt").exists())
        System.setProperty("user.dir", userDir)
    }

    private fun createBuildGradleWithModules(modules: Set<Module>) {
        val country = "br"
        val genFileName = "/build.gradle"
        val template = "templates/build.gradle.template"
        val jsonPath = "templates/labels/build.gradle.json"
        val modulePath = "/gateway/private-area"
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(
            modules,
            genFileName,
            template,
            jsonPath
        )
    }

    private fun isFilesEquals(expectedPath: String, pathToCompare: String): Boolean {
        val reader1 = BufferedReader(FileReader(expectedPath))
        val reader2 = BufferedReader(FileReader(pathToCompare))
        var line1: String? = reader1.readLine()
        var line2: String? = reader2.readLine()
        var areEqual = true
        var lineNum = 1
        while (line1 != null || line2 != null) {
            if (line1 == null || line2 == null) {
                areEqual = false
                break
            } else if (!line1.equals(line2, ignoreCase = true)) {
                areEqual = false
                break
            }
            line1 = reader1.readLine()
            line2 = reader2.readLine()
            lineNum++
        }
        reader1.close()
        reader2.close()
        return if (areEqual) {
            true
        } else {
            println("Two files have different content. They differ at line $lineNum")
            println("Expected has $line1 and but has $line2 at line $lineNum")
            false
        }
    }
}