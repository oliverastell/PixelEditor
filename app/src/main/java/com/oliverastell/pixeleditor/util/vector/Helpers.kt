package com.oliverastell.pixeleditor.util.vector

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round


fun floor(vec: Vec2) = Vec2(floor(vec.x), floor(vec.y))
fun ceil(vec: Vec2) = Vec2(ceil(vec.x), ceil(vec.y))
fun round(vec: Vec2) = Vec2(round(vec.x), round(vec.y))