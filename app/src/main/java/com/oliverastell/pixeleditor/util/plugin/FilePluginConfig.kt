package com.oliverastell.pixeleditor.util.plugin

import kotlinx.serialization.Serializable

@Serializable
data class FilePluginConfig(
    val version: String? = null,
    val credits: List<String>? = null,
    val license: String? = null,
    val initializer: String? = null,
    val lib: String = ""
)