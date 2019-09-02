package com.mm.consumer.label.model

import com.mm.consumer.model.Module

data class LabelHolder(
    val labelModuleMap: Map<String, Map<Module, String?>>?
)