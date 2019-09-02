package com.mm.consumer.label

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.mm.consumer.label.model.LabelHolder
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.stream.Collectors

object LabelLoader {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    fun loadLabels(jsonPath: String): LabelHolder {
        javaClass.classLoader.getResourceAsStream(jsonPath).use { input ->
            val reader = BufferedReader(InputStreamReader(input ?: throw FileNotFoundException("$jsonPath not found")))
            val json = reader.lines().collect(Collectors.joining(System.lineSeparator()))
            return mapper.readValue(json)
        }
    }
}