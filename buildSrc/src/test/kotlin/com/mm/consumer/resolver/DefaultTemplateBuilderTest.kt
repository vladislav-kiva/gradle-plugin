package com.mm.consumer.resolver

import com.google.common.base.CaseFormat
import com.mm.consumer.DefaultTemplateBuilder
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class DefaultTemplateBuilderTest {

    @Test
    fun `should create build gradle file`(@TempDir testProjectDir: File) {
        System.setProperty("user.dir", testProjectDir.absolutePath.toString())
        val country = "br"
        val genFileName = "/build.gradle"
        val template = "templates/build.gradle.template"
        val jsonPath = "templates/labels/build.gradle.json"
        val modulePath = "/gateway/private-area"
        val labelReplacer = LabelReplacer(country, modulePath)
        val userDir = System.getProperty("user.dir")
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(
            setOf(Module.KOTLIN, Module.JOOQ),
            genFileName,
            template,
            jsonPath
        )
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/build.gradle").exists())
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmapp kt file`(@TempDir testProjectDir: File) {
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
        val template = "templates/MMAppTemplate.kt.template"
        val labelReplacer = LabelReplacer(country, modulePath)
        val userDir = System.getProperty("user.dir")
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(setOf(Module.KOTLIN, Module.JOOQ), genFileName, template, null)
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/BrPrivateAreaApp.kt").exists())
        System.setProperty("user.dir", userDir)
    }

    @Test
    fun `should create mmApp mmConfig rabbitConfig and mmController`(@TempDir testProjectDir: File) {
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
        val userDir = System.getProperty("user.dir")
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)

        var genFileName = "/src/kotlin/com/mm$modulePath/$countryCamel${appCamel}App.kt"
        var template = "templates/MMAppTemplate.kt.template"

        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        genFileName = "/src/kotlin/com/mm$modulePath/config/$countryCamel${appCamel}Config.kt"
        template = "templates/Config.kt.template"

        builder.build(setOf(Module.KOTLIN), genFileName, template, "templates/labels/config.kt.json")

        genFileName = "/src/kotlin/com/mm$modulePath/controller/$countryCamel${appCamel}Controller.kt"
        template = "templates/MMController.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)


        genFileName = "/src/kotlin/com/mm$modulePath/config/$countryCamel${appCamel}RabbitConfig.kt"
        template = "templates/Rabbit.kt.template"
        builder.build(setOf(Module.KOTLIN), genFileName, template, null)

        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/config/BrPrivateAreaRabbitConfig.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/controller/BrPrivateAreaController.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/config/BrPrivateAreaConfig.kt").exists())
        Assertions.assertTrue(File(testProjectDir.absolutePath + "/gateway/private-area/src/kotlin/com/mm/gateway/private-area/BrPrivateAreaApp.kt").exists())
        System.setProperty("user.dir", userDir)
    }
}