package com.oliverastell.pixeleditor.util

import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.isDirectory
import kotlin.io.path.notExists

class FileSystem(
    val rootFolder: Path,
    pluginsFolderName: String = "plugins"
) {
    val pluginsFolder: Path = rootFolder.resolve(pluginsFolderName)

    init {
        if (pluginsFolder.notExists() || !pluginsFolder.isDirectory())
            pluginsFolder.createDirectory()
    }
}