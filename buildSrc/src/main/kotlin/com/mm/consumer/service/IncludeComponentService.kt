package com.mm.consumer.service

import com.mm.consumer.resolver.FileFinder
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object IncludeComponentService {

    fun injectImport(country: String, modulePath: String) {
        val includeDomain = modulePath.toLowerCase().replace("/", ":")
        val include = "includeComponent ':${country.toLowerCase()}$includeDomain', ${country.toUpperCase()}, CHANGEME"
        val finder = FileFinder()
        val settingsFile = finder.find("settings.gradle", { file -> file.isFile }, System.getProperty("user.dir"))
        val lines = settingsFile.readLines().toMutableList()
        var lastApproach = 0
        lines.forEachIndexed { index, line ->
            if (line.matches(Regex("includeComponent+ ':.*"))) {
                lastApproach = index
            }
        }
        lines.add(lastApproach + 1, include)
        Files.write(settingsFile.toPath(), lines, StandardCharsets.UTF_8);
    }
}