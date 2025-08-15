package com.oliverastell.pixeleditor.util.plugin.core

import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Namespace
import com.oliverastell.pixeleditor.util.IdentifierPath
import com.oliverastell.pixeleditor.util.core.CoreIdentifiers
import com.oliverastell.pixeleditor.util.plugin.DynamicObject
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects.dynamic
import com.oliverastell.pixeleditor.util.plugin.DynamicPackageObject
import com.oliverastell.pixeleditor.util.plugin.Plugin
import com.oliverastell.pixeleditor.util.settings.Setting

class CorePlugin(
    override val loader: Loader
) : Plugin {
    override val namespace = Namespace.fromString("core")
    override val settings = HashMap<IdentifierPath, Setting<*>>()

    init {
        settings[CoreIdentifiers.PenWidth.path] = Setting.clampedNumber(1.0, 1.0..20.0)
        settings[CoreIdentifiers.Color.path] = Setting.color(Color.White)
    }

    override val rootModule: DynamicObject<*> = DynamicPackageObject().apply {
//        set("coolprint", KotlinDynamicSandboxedObject(KotlinDynamicCallable { args ->
//            println("WOW SUPER COOL! $args")
//        }))
        set("coolprint", { args: List<Any> ->
            println("WOW SUPER COOL! $args")
        }.dynamic())
    }

    override fun getLibModule(path: IdentifierPath): DynamicObject<*>? = getModule(path)

    override fun initialize() {}
}