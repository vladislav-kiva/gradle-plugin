package com.mm.consumer

import com.mm.consumer.model.Module
import com.mm.consumer.service.ModuleFileResolverService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ModuleFileResolverServiceTest {

    @Test
    fun `should return path for country and module`() {
        val resolver = ModuleFileResolverService("mx", "/gateway/registration")
        val ktFiles = resolver.getFilesForModules(Module.KOTLIN)!![Module.KOTLIN]
        val value = ktFiles?.get("/src/kotlin/com/mm/mx/gateway/registration/MxRegistrationApp.kt")
        Assertions.assertNotNull(value)
    }
}