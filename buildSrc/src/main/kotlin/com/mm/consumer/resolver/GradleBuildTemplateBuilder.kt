package com.mm.consumer.resolver

import com.mm.consumer.label.LabelLoader
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.stream.Collectors

class GradleBuildTemplateBuilder(
    modulePath: String,
    country: String,
    private val fileCreator: FileCreator
) : TemplateBuilder {

    private val fullPath: String = System.getProperty("user.dir") + modulePath
    private val genFileName = "/build.gradle"
    private val template = "templates/build.gradle.template"
    private val jsonPath = "templates/labels/build.gradle.json"
    private val labelReplacer = LabelReplacer(country, modulePath)

    override fun build(modules: Map<Module, String>) {
        val genFilePath = Paths.get(fullPath + genFileName)
        fileCreator.createFileWithDirectories(genFilePath.toFile())
        javaClass.classLoader.getResourceAsStream(template).use { input ->
            FileOutputStream(genFilePath.toFile()).use { fileOutStream ->
                input ?: throw FileNotFoundException("File = $template not found")
                val labels = LabelLoader.loadLabels(jsonPath)
                val reader = BufferedReader(InputStreamReader(input))
                val template = reader.lines().collect(Collectors.joining(System.lineSeparator()))
                val result = labelReplacer.replaceLabels(template, labels.labelModuleMap, modules.keys)
                fileOutStream.write(result.toByteArray())
            }
        }
        val labels = LabelLoader.loadLabels(jsonPath)
        println(labels)
    }

}