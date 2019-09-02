package com.mm.consumer.model

/*
spring5  (java + kotlin)
- jooq
- rabbit
- mongo
- restConnector(feign)
Build.gradle
endpoint
 */
enum class Module {
    INIT,
    JAVA,
    KOTLIN,

    RABBIT,

    MONGO,
    JOOQ,
    REQUERY,

    FEIGN,
    RETROFIT
}