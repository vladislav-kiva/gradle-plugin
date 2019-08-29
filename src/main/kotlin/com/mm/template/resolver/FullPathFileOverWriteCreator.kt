package com.mm.template.resolver

import java.io.File

object FullPathFileOverWriteCreator : FileCreator {

  override fun createFileWithDirectories(file: File) {
    if (file.exists()) {
      file.delete()
    } else {
      file.parentFile.mkdirs()
    }
    file.createNewFile()
  }
}