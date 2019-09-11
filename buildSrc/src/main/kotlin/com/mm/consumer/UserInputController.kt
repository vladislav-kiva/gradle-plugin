package com.mm.consumer

import com.mm.consumer.model.Module
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object UserInputController {

    private val allModules = Module.values().map { module -> module.name }

    fun inputCountry(inputStream: InputStream = System.`in`): String {
        var userInputCountry: String? = null
        val reader = BufferedReader(InputStreamReader(inputStream))
        do {
            println("Specify country code (e.g. mx, br, ru and etc.)")
            val userCountry = reader.readLine()
            if (userCountry != null && userCountry.matches(Regex("^[a-z]{2,5}$"))) {
                userInputCountry = userCountry
            }
        } while (userInputCountry == null)
        return userInputCountry
    }

    fun inputModulePath(inputStream: InputStream = System.`in`): String {
        var userInputModulePath: String? = null
        val reader = BufferedReader(InputStreamReader(inputStream))
        do {
            println("Specify module path (e.g. /gateway/registration)")
            val userModulePath = reader.readLine()
            if (userModulePath != null && userModulePath.matches(Regex("^/([A-z0-9-_+]+/)*([A-z0-9]+)\$"))) {
                userInputModulePath = userModulePath
            }
        } while (userInputModulePath == null)
        return userInputModulePath
    }

    fun inputModules(inputStream: InputStream = System.`in`): List<Module> {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val resolved = mutableListOf<Module>()
        do {
            println("Specify included modules (${Module.values().joinToString()})")
            val userInput = reader.readLine()
            val input = userInput?.split(", ", " ", ",")
            input?.forEach { value ->
                if (allModules.contains(value.toUpperCase())) {
                    resolved.add(Module.valueOf(value.toUpperCase()))
                } else {
                    println("No such module = $value")
                    resolved.clear()
                }
            }
            if (resolved.size > 0 && !resolved.contains(Module.KOTLIN) && !resolved.contains(Module.JAVA)) {
                resolved.add(Module.KOTLIN)
            }
        } while (resolved.isEmpty())
        return resolved
    }
}
