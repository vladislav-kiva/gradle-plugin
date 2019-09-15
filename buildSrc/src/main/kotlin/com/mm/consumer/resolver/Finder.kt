package com.mm.consumer.resolver

import java.io.File

interface Finder {

    fun find(fileToFind: String, condition: (File) -> Boolean, rootDir: String): File
}