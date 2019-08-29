package com.mm.template.resolver

import java.io.File
import java.nio.file.Path

interface FileCreator {

  fun createFileWithDirectories(file: File)
}
