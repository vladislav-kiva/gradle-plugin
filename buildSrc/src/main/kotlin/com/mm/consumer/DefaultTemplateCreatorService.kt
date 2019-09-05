package com.mm.consumer

import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import com.mm.consumer.resolver.FullPathFileOverWriteCreator

object DefaultTemplateCreatorService : TemplateCreatorService {

    private val fileToTemplate = mapOf(
        "/build.gradle" to mapOf("templates/build.gradle.template" to "templates/labels/build.gradle.json")
    )

    override fun createTemplate(moduleExtension: ModuleExtension) {
        var country: String?
        var modulePath: String?
        var modules: Set<Module>
        do {
            country = UserInputService.inputCountry()
        } while (country == null)
        do {
            modulePath = UserInputService.inputModulePath()
        } while (modulePath == null)
        do {
            modules = UserInputService.inputModules().toSet()
        } while (modules.isEmpty())
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = DefaultTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        builder.build(modules, "/build.gradle", "templates/build.gradle.template", "templates/labels/build.gradle.json")
        builder.build(modules, "/src/com/mm/$country/${modulePath.toLowerCase()}/", "templates/build.gradle.template", "templates/labels/build.gradle.json")
    }
}