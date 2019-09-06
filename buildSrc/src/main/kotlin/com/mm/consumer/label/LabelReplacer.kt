package com.mm.consumer.label

import com.google.common.base.CaseFormat
import com.mm.consumer.model.Module
import org.apache.log4j.LogManager

class LabelReplacer(country: String, modulePath: String) {

    private val logger = LogManager.getLogger(LabelReplacer::class.java)
    private val subModule = modulePath.substringAfterLast("/", modulePath.substringAfterLast("\\\\"))
    private val defaults = mapOf(
        "#{submoduleCamel}" to CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, subModule),
        "#{submoduleLowerHyphen}" to CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, subModule),
        "#{countryLowercase}" to country.toLowerCase(),
        "#{countryCamel}" to country[0].toUpperCase() + country.substring(1).toLowerCase(),
        "#{countryUppercase}" to country.toUpperCase(),
        "#{package}" to "com.mm.$country${modulePath.toLowerCase().replace(Regex("[\\\\/]"), ".")}"
    )

    fun replaceLabels(text: String, labels: Map<String, Map<Module, String>?>?, includedModules: Set<Module>): String {
        var result = text
        labels?.forEach { (label, moduleMap) ->
            if (text.contains(label)) {
                val configuredModules = moduleMap?.filter { entry -> includedModules.contains(entry.key) }
                if (configuredModules != null) {
                    moduleMap.filter { entry -> entry.key == Module.INIT }
                        .forEach { entry -> result = result.replace("#{$label}", entry.value) }
                    configuredModules.forEach { entry -> result = result.replace("#{$label}", entry.value) }
                }
            } else {
                logger.warn("Label $label not found")
            }
        }
        defaults.forEach { entry -> result = result.replace(entry.key, entry.value) }
        return result.replace(Regex("#\\{.*?}[\\r\\n]*"), "")
    }
}