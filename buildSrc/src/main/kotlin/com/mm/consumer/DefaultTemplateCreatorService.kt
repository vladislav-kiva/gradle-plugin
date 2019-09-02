package com.mm.consumer

import com.mm.consumer.resolver.FullPathFileOverWriteCreator
import com.mm.consumer.resolver.GradleBuildTemplateBuilder

object DefaultTemplateCreatorService : TemplateCreatorService {

    private var country: String? = null
    private var modulePath: String? = null

    override fun createTemplate(moduleExtension: ModuleExtension) {
        val fileCreator = FullPathFileOverWriteCreator
        do {
            country = UserInputService.inputCountry()
        } while (country == null)
        do {
            modulePath = UserInputService.inputModulePath()
        } while (modulePath == null)
        val gradleBuildTemplateBuilder = GradleBuildTemplateBuilder(
            modulePath ?: throw IllegalStateException("No module specified"),
            country ?: throw java.lang.IllegalStateException("No country specified"),
            fileCreator
        )
        gradleBuildTemplateBuilder.build(emptyMap())
    }
}