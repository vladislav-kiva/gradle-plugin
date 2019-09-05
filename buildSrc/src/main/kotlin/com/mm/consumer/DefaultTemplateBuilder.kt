package com.mm.consumer

import com.mm.consumer.label.LabelLoader
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import com.mm.consumer.resolver.FileCreator
import com.mm.consumer.resolver.TemplateBuilder
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.stream.Collectors

class DefaultTemplateBuilder(
    modulePath: String,
    private val labelReplacer: LabelReplacer,
    private val fileCreator: FileCreator
) : TemplateBuilder {

    private val fullPath: String = System.getProperty("user.dir") + modulePath

    override fun build(modules: Set<Module>, innerFilePath: String, templatePath: String, labelsPath: String?) {
        val genFilePath = Paths.get(fullPath + innerFilePath)
        fileCreator.createFileWithDirectories(genFilePath.toFile())
        javaClass.classLoader.getResourceAsStream(templatePath).use { input ->
            FileOutputStream(genFilePath.toFile()).use { fileOutStream ->
                input ?: throw FileNotFoundException("File = $templatePath not found")
                val labels = labelsPath?.let { path -> LabelLoader.loadLabels(path) }
                val reader = BufferedReader(InputStreamReader(input))
                val template = reader.lines().collect(Collectors.joining(System.lineSeparator()))
                val result = labelReplacer.replaceLabels(template, labels?.labelModuleMap, modules)
                fileOutStream.write(result.toByteArray())
            }
        }
    }

}