package com.oliverastell.pixeleditor.util.resources

import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2

interface Tool : Resource, SettingsHolder {
    val icon: Identifier

    fun dragStart(appState: EditorAppState, pos: Vec2) {}

    fun dragMoved(appState: EditorAppState, pos: Vec2) {}

    fun dragEnded(appState: EditorAppState, pos: Vec2) {}
}