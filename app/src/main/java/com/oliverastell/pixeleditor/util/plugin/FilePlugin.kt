package com.oliverastell.pixeleditor.util.plugin

import com.akuleshov7.ktoml.Toml
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Namespace
import com.oliverastell.pixeleditor.util.IdentifierPath
import com.oliverastell.pixeleditor.util.settings.Setting
import kotlinx.serialization.decodeFromString
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText


class FilePlugin(
    override val loader: Loader,
    override val namespace: Namespace,
    val path: Path
) : Plugin {

    val configs: FilePluginConfig = loadConfigsIn(path)

    override val settings = HashMap<IdentifierPath, Setting<*>>()

    override val rootModule: DynamicObject<*> = loadModuleRecursive(path, IdentifierPath())!!

    override fun getLibModule(path: IdentifierPath): DynamicObject<*>? {
        return getModule(IdentifierPath.fromString(configs.lib).withSuffix(path))
    }

    private fun loadConfigsIn(path: Path, configsName: String = "plugin.toml") = Toml.decodeFromString<FilePluginConfig>(
        path.resolve(configsName).readText()
    )

    private fun loadModuleRecursive(path: Path, identifierPath: IdentifierPath): DynamicObject<*>? {
        val identifier = Identifier(namespace, identifierPath)

        if (path.isDirectory()) {
            val module = DynamicPackageObject()

            for (subPath in Files.list(path)) {
                val moduleName = subPath.nameWithoutExtension
                val subModule = loadModuleRecursive(
                    subPath, identifierPath.withSuffix(moduleName)
                )
                if (subModule == null)
                    continue
                module[moduleName] = subModule
            }

            return module
        } else if (DynamicObject.supports(path.extension)) {
            val module = DynamicObject.fromPath(this, identifier, path)
            return module
        }

        return null
    }

    override fun initialize() {
        if (configs.initializer == null)
            return

        val path = IdentifierPath.fromString(configs.initializer)
        val module = getModule(path) ?: throw InvalidIdentifier()

        module(listOf())
    }
}
