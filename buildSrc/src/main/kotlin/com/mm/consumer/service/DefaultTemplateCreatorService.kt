package com.mm.consumer.service

import com.mm.consumer.SrcFileTemplateBuilder
import com.mm.consumer.ModuleExtension
import com.mm.consumer.UserInputController
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import com.mm.consumer.resolver.FullPathFileOverWriteCreator

class DefaultTemplateCreatorService(
    private val userInputService: UserInputController = UserInputController,
    private val configCreatorService: ConfigCreatorService = ConfigCreatorService,
    private val includeComponentService: IncludeComponentService = IncludeComponentService
) : TemplateCreatorService {

    override fun createTemplate(moduleExtension: ModuleExtension) {
        val country = userInputService.inputCountry()
        val modulePath = userInputService.inputModulePath()
        val modules = userInputService.inputModules().toSet()
        val labelReplacer = LabelReplacer(country, modulePath)
        val builder = SrcFileTemplateBuilder(modulePath, labelReplacer, FullPathFileOverWriteCreator)
        val resolver = ModuleFileResolverService(country, modulePath)
        if (modules.contains(Module.KOTLIN)) {
            val kotlinModules = resolver.getFilesForModules(Module.KOTLIN)
            modules.forEach { userModule ->
                kotlinModules?.get(userModule)?.forEach { kotlinModule ->
                    builder.build(modules, kotlinModule.key, kotlinModule.value.first, kotlinModule.value.second)
                }
            }
        } else if (modules.contains(Module.JAVA)) {
            val javaModules = resolver.getFilesForModules(Module.JAVA)
            modules.forEach { userModule ->
                javaModules?.get(userModule)?.forEach { kotlinModule ->
                    builder.build(modules, kotlinModule.key, kotlinModule.value.first, kotlinModule.value.second)
                }
            }
        }
        configCreatorService.createConfig(modules, country, modulePath)
        includeComponentService.injectImport(country, modulePath)
    }
}