package com.mm.consumer.resolver

import java.io.File
import java.io.FileNotFoundException

class DirectoryFinder : Finder {

    private val userDir = System.getProperty("user.dir")

    override fun find(fileToFind: String): File {
        var directory: File? = File(userDir)
        while (directory != null) {
            val foundDir = directory.listFiles()?.asSequence()
                ?.filter { file -> file.isDirectory && file.endsWith(fileToFind) }
                ?.firstOrNull()
            if (foundDir != null) {
                return foundDir
            }
            directory = directory.parentFile
        }
        throw FileNotFoundException("Not found directory = $fileToFind")
    }
}