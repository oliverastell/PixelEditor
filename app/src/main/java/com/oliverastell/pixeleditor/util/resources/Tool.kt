package com.oliverastell.pixeleditor.util.resources

import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.vector.Vec2
import com.oliverastell.pixeleditor.util.vector.Vec2i

interface Tool : Resource, SettingsHolder {

    fun dragStart(pos: Vec2) {}

    fun dragMoved(pos: Vec2) {}

    fun dragEnded(pos: Vec2) {}
}