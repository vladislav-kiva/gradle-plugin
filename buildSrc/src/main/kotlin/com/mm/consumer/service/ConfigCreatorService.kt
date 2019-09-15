package com.mm.consumer.service

import com.mm.consumer.SrcFileTemplateBuilder
import com.mm.consumer.label.LabelReplacer
import com.mm.consumer.model.Module
import com.mm.consumer.resolver.FileFinder
import com.mm.consumer.resolver.FullPathFileOverWriteCreator
import java.io.File

object ConfigCreatorService {

    fun createConfig(modules: Set<Module>, country: String, modulePath: String) {
        val countryLowerCase = country.toLowerCase()
        val finder = FileFinder()
        val mmConfigFolder = finder.find("mm-config", { file -> file.isDirectory }, System.getProperty("user.dir"))
        val labelReplacer = LabelReplacer(countryLowerCase, modulePath)
        val submodule = labelReplacer.defaults["#{submoduleLowerHyphen}"]
        val builder = SrcFileTemplateBuilder(
            "/$countryLowerCase",
            labelReplacer,
            FullPathFileOverWriteCreator,
            mmConfigFolder.path
        )
        builder.build(
            modules,
            "/$countryLowerCase-$submodule.yaml",
            "templates/config.yaml.template",
            "templates/labels/config.yaml.json"
        )
    }
}