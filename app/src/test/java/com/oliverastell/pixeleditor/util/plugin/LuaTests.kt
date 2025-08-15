package com.oliverastell.pixeleditor.util.plugin

import com.oliverastell.pixeleditor.util.Loader
import org.junit.Test
import kotlin.io.path.Path

class LuaTests {

    @Test
    fun luaTest() {
        val loader = Loader(Path("D:\\Users\\thebr\\Desktop\\plugins"))

        loader.callInitializers()

    }
}