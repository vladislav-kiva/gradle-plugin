package com.mm.consumer.label

import com.mm.consumer.model.Module
import org.apache.log4j.LogManager

class LabelReplacer(private val country: String, private val modulePath: String) {

    private val logger = LogManager.getLogger(LabelReplacer::class.java)

    fun replaceLabels(text: String, labels: Map<String, Map<Module, String>?>, includedModules: Set<Module>): String {
        var result = text
        labels.forEach { (label, moduleMap) ->
            if (text.contains(label)) {
                val configuredModules = moduleMap?.filter { entry -> includedModules.contains(entry.key) }
                configuredModules?.filter { entry -> entry.key == Module.INIT && moduleMap.size > 1 }
                    ?.forEach { entry -> result = result.replace("#{$label}", entry.value) }
                configuredModules?.filter { entry -> entry.key != Module.INIT }
                    ?.forEach { entry -> result = result.replace("#{$label}", entry.value) }
            } else {
                logger.warn("Label $label not found")
            }
        }
        return result.replace(Regex("#\\{.*?}[\\r\\n]*"), "")
    }
}