package com.oliverastell.pixeleditor.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.oliverastell.pixeleditor.util.plugin.FilePlugin
import com.oliverastell.pixeleditor.util.plugin.Plugin
import com.oliverastell.pixeleditor.util.plugin.core.CorePlugin
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.iterator
import kotlin.io.path.nameWithoutExtension

class Loader(val pluginsFolder: Path) {
    var editor: Editor? by mutableStateOf(null)
    private val plugins = HashMap<Namespace, Plugin>()
    var pluginsAreInitialized = false
        private set

    init {
        loadArbitraryPlugin(CorePlugin(this))

        for (path in Files.list(pluginsFolder)) {
            loadArbitraryPlugin(path)
        }

        pluginsAreInitialized = true
    }

    fun isInitialized(namespace: Namespace) = plugins.containsKey(namespace)

    fun getSetting(identifier: Identifier) = plugins.get(identifier.namespace)?.getSetting(identifier.path)
    fun getModule(identifier: Identifier) = plugins.get(identifier.namespace)?.getModule(identifier.path)
    fun getLibModule(identifier: Identifier) = plugins.get(identifier.namespace)?.getLibModule(identifier.path)

    fun getPlugin(namespace: Namespace) = plugins[namespace]

    fun getOrLoadPlugin(namespace: Namespace): Plugin {
        if (!isInitialized(namespace))
            loadPlugin(namespace.name)
        return plugins[namespace]!!
    }

    fun loadPlugin(name: String) {
        val subPath = pluginsFolder.resolve(name)
        loadArbitraryPlugin(subPath)
    }

    fun loadArbitraryPlugin(pluginPath: Path) {
        val namespace = Namespace.fromString(pluginPath.nameWithoutExtension)
        if (isInitialized(namespace)) {
            println("Plugin $namespace already loaded, skipping")
            return
        }

        plugins[namespace] = FilePlugin(this, namespace, pluginPath)
    }

    fun loadArbitraryPlugin(plugin: Plugin) {
        plugins[plugin.namespace] = plugin
    }

    fun callInitializers() {
        for ((_, plugin) in plugins) {
            plugin.initialize()
        }
    }
}