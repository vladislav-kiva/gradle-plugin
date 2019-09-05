package com.mm.consumer

import com.mm.consumer.model.Module

object UserInputService {

    fun inputCountry(): String? {
        println("Specify country code (e.g. mx,br,ru and etc.)")
        val userCountry = readLine()
        return if (userCountry != null && userCountry.matches(Regex("^[a-z]{2,5}$"))) {
            userCountry
        } else {
            null
        }
    }

    fun inputModulePath(): String? {
        println("Specify module path (e.g. /gateway/registration)")
        val userModulePath = readLine()
        return if (userModulePath != null && userModulePath.matches(Regex("^/|(/[\\w-]+)+\$"))) {
            userModulePath
        } else {
            null
        }
    }

    fun inputModules(): List<Module> {
        println("Specify included modules (e.g. ${Module.values()}")
        val userInput = readLine()
        val test = mutableListOf<Module>()
        val modules = userInput?.split(",", " ")
            ?.map { input -> Module.valueOf(input) }
            ?.toCollection(test)
        return if (modules != null && modules.size > 0) {
            modules
        } else {
            emptyList()
        }
    }
}
