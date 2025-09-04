package com.oliverastell.pixeleditor.common

import java.text.DecimalFormat

val df = DecimalFormat("#.###")
fun Number.toFancyString(): String = df.format(this)
