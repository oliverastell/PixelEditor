package com.oliverastell.pixeleditor.util.resources

import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.vector.Vec2

interface Texture : Resource {

    fun getColor(topLeft: Vec2, bottomRight: Vec2, current: Vec2): Color

}