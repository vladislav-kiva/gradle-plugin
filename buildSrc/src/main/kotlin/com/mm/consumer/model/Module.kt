package com.mm.consumer.model

import com.fasterxml.jackson.annotation.JsonValue

/*
spring5  (java + kotlin)
- jooq
- rabbit
- mongo
- restConnector(feign)
Build.gradle
endpoint
 */
enum class Module(@get:JsonValue val _name: String) {
    INIT("init"),
    JAVA("java"),
    KOTLIN("kotlin"),

    RABBIT("rabbit"),

    MONGO("mongo"),
    JOOQ("jooq"),

    FEIGN("feign"),
    RETROFIT("retrofit")
}