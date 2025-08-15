package com.oliverastell.pixeleditor.util.resources

import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.vector.Vec2

interface Mask : Resource {

    fun getTransparency(topLeft: Vec2, bottomRight: Vec2, current: Vec2): Double

}