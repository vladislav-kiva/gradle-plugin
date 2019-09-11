package com.mm.consumer

import com.mm.consumer.model.Module
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

internal class UserInputControllerTest {

    @Test
    fun `input country should return country code`() {
        val mockedInputStream = ByteArrayInputStream("Brazil\nbrazil\nbr".toByteArray())
        val result = UserInputController.inputCountry(mockedInputStream)
        Assertions.assertEquals("br", result)
    }

    @Test
    fun `input module path should return correct inputted path`() {
        val mockedInputStream = ByteArrayInputStream("/registration/data".toByteArray())
        val result = UserInputController.inputModulePath(mockedInputStream)
        Assertions.assertEquals("/registration/data", result)
    }

    @Test
    fun `input module path should return correct inputted path with numbers`() {
        val mockedInputStream = ByteArrayInputStream("123123\n/registration/data2123".toByteArray())
        val result = UserInputController.inputModulePath(mockedInputStream)
        Assertions.assertEquals("/registration/data2123", result)
    }

    @Test
    fun `input module path should not return incorrect path`() {
        val mockedInputStream = ByteArrayInputStream("asdasdasd\n/correct".toByteArray())
        val result = UserInputController.inputModulePath(mockedInputStream)
        Assertions.assertEquals("/correct", result)
    }

    @Test
    fun `input modules should be case insensitive`() {
        val mockedInputStream = ByteArrayInputStream("kotlin".toByteArray())
        val result = UserInputController.inputModules(mockedInputStream)
        Assertions.assertEquals(listOf(Module.KOTLIN), result)
    }

    @Test
    fun `if no language specified should chose kotlin`() {
        val mockedInputStream = ByteArrayInputStream("jooq".toByteArray())
        val result = UserInputController.inputModules(mockedInputStream)
        val expected = listOf(Module.KOTLIN, Module.JOOQ)
        Assertions.assertTrue(
            expected.size == result.size && result.containsAll(expected) && expected.containsAll(result)
        )
    }

    @Test
    fun `if incorrect module specified should reask`() {
        val mockedInputStream = ByteArrayInputStream("java, kotlinasd\njava, kotlin".toByteArray())
        val result = UserInputController.inputModules(mockedInputStream)
        val expected = listOf(Module.KOTLIN, Module.JAVA)
        Assertions.assertTrue(
            expected.size == result.size && result.containsAll(expected) && expected.containsAll(result)
        )
    }
}