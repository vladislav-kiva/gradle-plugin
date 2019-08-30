package com.mm.consumer

import com.mm.consumer.resolver.FullPathFileOverWriteCreator
import com.mm.consumer.resolver.GradleBuildTemplateBuilder

object DefaultTemplateCreatorService : TemplateCreatorService {

  private var country: String? = null
  private var modulePath: String? = null
  private val userDir = System.getProperty("user.dir")

  override fun createTemplate(moduleExtension: ModuleExtension) {
    val fileCreator = FullPathFileOverWriteCreator
    do {
      country = UserInputService.inputCountry()
    } while (country == null)
    do {
      modulePath = UserInputService.inputModulePath()
    } while (modulePath == null)
    val gradleBuildTemplateBuilder = GradleBuildTemplateBuilder(userDir + modulePath, fileCreator)
    gradleBuildTemplateBuilder.build(emptyMap())
    println("Hola ->>>>>>")
  }
}