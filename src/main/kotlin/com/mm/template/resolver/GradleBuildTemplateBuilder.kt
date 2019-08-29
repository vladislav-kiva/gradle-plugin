package com.mm.template.resolver

import com.mm.template.model.Module
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.nio.file.Paths

class GradleBuildTemplateBuilder(
    private val rootPath: String,
    private val fileCreator: FileCreator
) : TemplateBuilder {

  private val genFileName = "build.gradle"

  override fun build(modules: Map<Module, String>) {
    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("", "HelloWorld")
        .addType(TypeSpec.classBuilder("Greeter")
            .primaryConstructor(FunSpec.constructorBuilder()
                .addParameter("name", String::class)
                .build())
            .addProperty(PropertySpec.builder("name", String::class)
                .initializer("name")
                .build())
            .addFunction(FunSpec.builder("greet")
                .addStatement("println(%P)", "Hello, \$name")
                .build())
            .build())
        .addFunction(FunSpec.builder("main")
            .addParameter("args", String::class, KModifier.VARARG)
            .addStatement("%T(args[0]).greet()", greeterClass)
            .build())
        .build()
    val genFile = Paths.get(rootPath + genFileName).toFile()
    fileCreator.createFileWithDirectories(genFile)
    file.writeTo(genFile)
  }
}