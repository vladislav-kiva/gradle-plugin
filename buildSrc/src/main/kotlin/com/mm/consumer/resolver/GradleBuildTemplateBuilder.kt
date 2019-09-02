package com.mm.consumer.resolver

import com.mm.consumer.label.LabelLoader
import com.mm.consumer.model.Module
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.file.Paths

class GradleBuildTemplateBuilder(
    private val rootPath: String,
    private val fileCreator: FileCreator
) : TemplateBuilder {

    private val genFileName = "/build.gradle"
    private val template = "templates/build.gradle.template"
    private val jsonPath = "templates/labels/build.gradle.json"

    override fun build(modules: Map<Module, String>) {
        val genFilePath = Paths.get(rootPath + genFileName)
        fileCreator.createFileWithDirectories(genFilePath.toFile())
        javaClass.classLoader.getResourceAsStream(template).use { input ->
            FileOutputStream(genFilePath.toFile()).use { fileOutStream ->
                input ?: throw FileNotFoundException("File = $template not found")
                input.copyTo(fileOutStream)
            }
        }
        val labels = LabelLoader.loadLabels(jsonPath)
        println(labels)
    }

}