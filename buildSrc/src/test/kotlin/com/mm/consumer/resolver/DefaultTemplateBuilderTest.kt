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
        val generatedFile = File(testProjectDir.absolutePath + "/gateway/registration/build.gradle")
        val expected = javaClass.classLoader.getResource("gradle/just-kotlin-build.gradle")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(isFilesEquals(expected!!.path, generatedFile.path))
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create build gradle file with java`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        createBuildGradleWithModules(setOf(Module.JAVA))
        val generatedFile = File(testProjectDir.absolutePath + "/gateway/registration/build.gradle")
        val expected = javaClass.classLoader.getResource("gradle/just-java-build.gradle")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(isFilesEquals(expected!!.path, generatedFile.path))
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
        val generatedFile = File(testProjectDir.absolutePath + "/gateway/registration/build.gradle")
        val expected = javaClass.classLoader.getResource("gradle/all-kotlin-build.gradle")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(isFilesEquals(expected!!.path, generatedFile.path))
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmapp kt file`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val countryCamel = country[0].toUpperCase() + country.substring(1).toLowerCase()
        val modulePath = "/gateway/registration"
        val appCamel = if (modulePath.contains("/")) {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
        } else {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
        }
        val genFileName = "/src/kotlin/com/mm/$country$modulePath/$countryCamel${appCamel}App.kt"
        val template = "templates/kotlin/MMAppTemplate.kt.template"
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(setOf(Module.KOTLIN, Module.JOOQ), genFileName, template, null)
        val generatedFile = File(
            testProjectDir.absolutePath + "/gateway/registration$genFileName"
        )
        val expected = javaClass.classLoader.getResource("kt/BrRegistrationApp.kt")
        Assertions.assertTrue(generatedFile.exists())
        Assertions.assertNotNull(expected)
        Assertions.assertTrue(isFilesEquals(expected!!.path, generatedFile.path))
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmConfig with all modules`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val countryCamel = country[0].toUpperCase() + country.substring(1).toLowerCase()
        val modulePath = "/gateway/registration"
        val appCamel = if (modulePath.contains("/")) {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
        } else {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
        }
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)

        var genFileName = "/src/kotlin/com/mm/$country$modulePath/$countryCamel${appCamel}App.kt"
        var template = "templates/kotlin/MMAppTemplate.kt.template"

        builder.build(setOf(Module.KOTLIN, Module.JOOQ, Module.RABBIT, Module.MONGO), genFileName, template, null)

        genFileName = "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}Config.kt"
        template = "templates/kotlin/Config.kt.template"

        builder.build(
            setOf(Module.KOTLIN, Module.JOOQ, Module.RABBIT, Module.MONGO),
            genFileName,
            template,
            "templates/labels/kotlin/config.kt.json"
        )

        genFileName = "/src/kotlin/com/mm/$country$modulePath/controller/$countryCamel${appCamel}Controller.kt"
        template = "templates/kotlin/MMController.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        genFileName = "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}JooqConfig.kt"
        template = "templates/kotlin/JooqConfig.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        genFileName = "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}EventBusConfig.kt"
        template = "templates/kotlin/Rabbit.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        val expected = javaClass.classLoader.getResource("kt/BrRegistrationConfig.kt")
        val configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/kotlin/com/mm/$country$modulePath/config/BrRegistrationConfig.kt")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/registration/src/kotlin/com/mm/$country$modulePath/controller/BrRegistrationController.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/registration/src/kotlin/com/mm/$country$modulePath/config/BrRegistrationConfig.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/registration/src/kotlin/com/mm/$country$modulePath/BrRegistrationApp.kt").exists())
        System.setProperty("user.dir", userDir)
    }


    @Test
    fun `should create all java modules`(@TempDir testProjectDir: File) {
        val userDir = System.getProperty("user.dir")
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val countryCamel = country[0].toUpperCase() + country.substring(1).toLowerCase()
        val modulePath = "/gateway/registration"
        val appCamel = if (modulePath.contains("/")) {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
        } else {
            CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
        }
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)

        var genFileName = "/src/java/com/mm/$country$modulePath/$countryCamel${appCamel}App.java"
        var template = "templates/java/MMAppTemplate.java.template"

        builder.build(setOf(Module.KOTLIN, Module.JOOQ, Module.RABBIT, Module.MONGO), genFileName, template, null)

        genFileName = "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}Config.java"
        template = "templates/java/Config.java.template"

        builder.build(
            setOf(Module.KOTLIN, Module.JOOQ, Module.RABBIT, Module.MONGO),
            genFileName,
            template,
            "templates/labels/java/config.java.json"
        )

        genFileName = "/src/java/com/mm/$country$modulePath/controller/$countryCamel${appCamel}Controller.java"
        template = "templates/java/MMController.java.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        genFileName = "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}JooqConfig.java"
        template = "templates/java/JooqConfig.java.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        genFileName = "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}EventBusConfig.java"
        template = "templates/java/Rabbit.java.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        var expected = javaClass.classLoader.getResource("java/BrRegistrationConfig.java")
        var configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/java/com/mm/$country$modulePath/config/BrRegistrationConfig.java")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))
        expected = javaClass.classLoader.getResource("java/BrRegistrationController.java")
        configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/java/com/mm/$country$modulePath/controller/BrRegistrationController.java")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))

        expected = javaClass.classLoader.getResource("java/BrRegistrationEventBusConfig.java")
        configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/java/com/mm/$country$modulePath/config/BrRegistrationEventBusConfig.java")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))

        expected = javaClass.classLoader.getResource("java/BrRegistrationJooqConfig.java")
        configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/java/com/mm/$country$modulePath/config/BrRegistrationJooqConfig.java")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))

        expected = javaClass.classLoader.getResource("java/BrRegistrationApp.java")
        configFile =
            File(testProjectDir.absolutePath + "/gateway/registration/src/java/com/mm/$country$modulePath/BrRegistrationApp.java")
        Assertions.assertTrue(configFile.exists())
        Assertions.assertTrue(isFilesEquals(expected!!.path, configFile.path))

        System.setProperty("user.dir", userDir)
    }

    private fun createBuildGradleWithModules(modules: Set<Module>) {
        val country = "br"
        val genFileName = "/build.gradle"
        val template = "templates/build.gradle.template"
        val jsonPath = "templates/labels/build.gradle.json"
        val modulePath = "/gateway/registration"
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