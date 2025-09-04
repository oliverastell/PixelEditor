package com.oliverastell.pixeleditor.util

import com.oliverastell.pixeleditor.util.plugin.FilePlugin
import com.oliverastell.pixeleditor.util.plugin.Plugin
import com.oliverastell.pixeleditor.util.plugin.core.CorePlugin
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.iterator
import kotlin.io.path.nameWithoutExtension

class Loader {
    val plugins = HashMap<Namespace, Plugin>()
    var pluginsAreInitialized = false
        private set

//    init {
//        loadPlugin(CorePlugin())
//
//        for (path in Files.list(pluginsFolder)) {
//            loadPlugin(path)
//        }
//
//        pluginsAreInitialized = true
//    }

    fun isInitialized(namespace: Namespace) = plugins.containsKey(namespace)

    fun getVisual(identifier: Identifier) = plugins.get(identifier.namespace)?.getVisual(identifier.path)
    fun getSetting(identifier: Identifier) = plugins.get(identifier.namespace)?.getSetting(identifier.path)
    fun getTool(identifier: Identifier) = plugins.get(identifier.namespace)?.getTool(identifier.path)
    fun getTexture(identifier: Identifier) = plugins.get(identifier.namespace)?.getTexture(identifier.path)

    fun getModule(identifier: Identifier) = plugins.get(identifier.namespace)?.getModule(identifier.path)
    fun getLibModule(identifier: Identifier) = plugins.get(identifier.namespace)?.getLibModule(identifier.path)

    fun getPlugin(namespace: Namespace) = plugins[namespace]

    fun getOrLoadPlugin(appState: AppState, namespace: Namespace): Plugin {
        if (!isInitialized(namespace))
            loadPlugin(appState, namespace.name)
        return plugins[namespace]!!
    }

    fun loadPlugins(appState: AppState) {
        for (path in Files.list(appState.fileSystem.pluginsFolder)) {
            loadPlugin(appState, path)
        }
    }

    fun loadPlugin(appState: AppState, name: String) {
        val subPath = appState.fileSystem.pluginsFolder.resolve(name)
        loadPlugin(appState, subPath)
    }

    fun loadPlugin(appState: AppState, pluginPath: Path) {
        val namespace = Namespace.fromString(pluginPath.nameWithoutExtension)
        if (isInitialized(namespace)) {
            println("Plugin $namespace already loaded, skipping")
            return
        }

        plugins[namespace] = FilePlugin(appState, namespace, pluginPath)
    }

    fun loadPlugin(plugin: Plugin) {
        plugins[plugin.namespace] = plugin
    }

    fun callInitializers() {
        for ((_, plugin) in plugins) {
            plugin.initialize()
        }
    }

    fun defaultInit(appState: AppState) {
        loadPlugin(CorePlugin(appState))
        loadPlugins(appState)
        callInitializers()
    }
}