package com.mm.consumer.resolver

import java.io.File
import java.io.FileNotFoundException

class FileFinder : Finder {

    override fun find(fileToFind: String, condition: (File) -> Boolean, rootDir: String): File {
        var directory: File? = File(rootDir)
        while (directory != null) {
            val foundDir = directory.listFiles()?.asSequence()
                ?.filter { file -> condition(file) && file.endsWith(fileToFind) }
                ?.firstOrNull()
            if (foundDir != null) {
                return foundDir
            }
            directory = directory.parentFile
        }
        throw FileNotFoundException("Not found file = $fileToFind")
    }
}