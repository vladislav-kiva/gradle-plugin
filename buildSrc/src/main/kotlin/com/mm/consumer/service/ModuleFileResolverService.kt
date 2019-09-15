package com.mm.consumer.service

import com.google.common.base.CaseFormat
import com.mm.consumer.model.Module

class ModuleFileResolverService(country: String, modulePath: String) {

    private val countryCamel: String = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, country)
    private val appCamel = if (modulePath.contains("/")) {
        CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath.substringAfterLast("/"))
    } else {
        CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, modulePath)
    }

    private val moduleToFilesMap = mapOf(
        Module.KOTLIN to mapOf(
            Module.KOTLIN to mapOf(
                "/build.gradle" to Pair("templates/build.gradle.template", "templates/labels/build.gradle.json"),
                "/src/kotlin/com/mm/$country$modulePath/$countryCamel${appCamel}App.kt" to Pair(
                    "templates/kotlin/MMAppTemplate.kt.template", null
                ),
                "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}Config.kt" to Pair(
                    "templates/kotlin/Config.kt.template", "templates/labels/kotlin/config.kt.json"
                ),
                "/src/kotlin/com/mm/$country$modulePath/controller/$countryCamel${appCamel}Controller.kt" to Pair(
                    "templates/kotlin/MMController.kt.template", null
                )
            ),
            Module.RABBIT to mapOf(
                "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}EventBusConfig.kt" to Pair(
                    "templates/kotlin/Rabbit.kt.template",
                    null
                )
            ),
            Module.JOOQ to mapOf(
                "/src/kotlin/com/mm/$country$modulePath/config/$countryCamel${appCamel}JooqConfig.kt" to Pair(
                    "templates/kotlin/JooqConfig.kt.template",
                    null
                )
            )
        ),
        Module.JAVA to mapOf(
            Module.JAVA to mapOf(
                "/build.gradle" to Pair("templates/build.gradle.template", "templates/labels/build.gradle.json"),
                "/src/java/com/mm/$country$modulePath/$countryCamel${appCamel}App.java" to Pair(
                    "templates/java/MMAppTemplate.java.template", null
                ),
                "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}Config.java" to Pair(
                    "templates/java/Config.java.template", "templates/labels/java/config.java.json"
                ),
                "/src/java/com/mm/$country$modulePath/controller/$countryCamel${appCamel}Controller.java" to Pair(
                    "templates/java/MMController.java.template", null
                )
            ),
            Module.RABBIT to mapOf(
                "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}EventBusConfig.java" to Pair(
                    "templates/java/Rabbit.java.template",
                    null
                )
            ),
            Module.JOOQ to mapOf(
                "/src/java/com/mm/$country$modulePath/config/$countryCamel${appCamel}JooqConfig.java" to Pair(
                    "templates/java/JooqConfig.java.template",
                    null
                )
            )
        )
    )

    fun getFilesForModules(module: Module) = moduleToFilesMap[module]
}