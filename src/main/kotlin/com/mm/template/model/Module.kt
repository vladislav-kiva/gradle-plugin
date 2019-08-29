package com.mm.template.model

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
  JAVA,
  KOTLIN,

  RABBIT,

  MONGO,
  JOOQ,
  REQUERY,

  FEIGN,
  RETROFIT
}