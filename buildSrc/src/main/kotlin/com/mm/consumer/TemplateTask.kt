package com.mm.consumer

import com.mm.consumer.model.Module
import com.mm.consumer.service.DefaultTemplateCreatorService
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class TemplateTask : DefaultTask() {

    var includedModules: List<Module>? = null
    var country: String? = null

    init {
        group = "idf"
    }

    @TaskAction
    internal fun generate() {
        var extension = project.extensions.findByType(ModuleExtension::class.java)
        if (extension == null) {
            extension = ModuleExtension()
        }
        DefaultTemplateCreatorService().createTemplate(extension)
    }
}