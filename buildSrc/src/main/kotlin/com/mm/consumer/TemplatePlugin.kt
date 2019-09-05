package com.mm.consumer

import com.mm.consumer.model.Module
import org.gradle.api.Plugin
import org.gradle.api.Project

//todo lazy loading
open class TemplatePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("templateSettings", ModuleExtension::class.java)
        project.tasks.create("generateTemplate", TemplateTask::class.java) { task ->
            task.includedModules = extension.includedModules
            task.country = extension.country
        }
    }
}