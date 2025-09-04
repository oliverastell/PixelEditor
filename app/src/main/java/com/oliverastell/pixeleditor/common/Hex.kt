package com.oliverastell.pixeleditor.common

import android.util.Log
import androidx.collection.MutableIntList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces


fun Char.hexToInt() = when (this) {
    '0' -> 0x0
    '1' -> 0x1
    '2' -> 0x2
    '3' -> 0x3
    '4' -> 0x4
    '5' -> 0x5
    '6' -> 0x6
    '7' -> 0x7
    '8' -> 0x8
    '9' -> 0x9
    'a' -> 0xA
    'A' -> 0xA
    'b' -> 0xB
    'B' -> 0xB
    'c' -> 0xC
    'C' -> 0xC
    'd' -> 0xD
    'D' -> 0xD
    'e' -> 0xE
    'E' -> 0xE
    'f' -> 0xF
    'F' -> 0xF
    else -> -1
}

fun String.hexToColor(): Color? {
    val hexSymbols = MutableIntList(this.length)
    for (symbol in this) {
        val value = symbol.hexToInt()
        if (value < 0)
            continue
        hexSymbols += value
    }

    return when (hexSymbols.size) {
        2 -> {
            val rgb = (hexSymbols[0] shl 4) or hexSymbols[1]
            Color(rgb, rgb, rgb)
        }
        6 -> {
            val red = (hexSymbols[0] shl 4) or hexSymbols[1]
            val green = (hexSymbols[2] shl 4) or hexSymbols[3]
            val blue = (hexSymbols[4] shl 4) or hexSymbols[5]
            Color(red, green, blue)
        }
        8 -> {
            val red = (hexSymbols[0] shl 4) or hexSymbols[1]
            val green = (hexSymbols[2] shl 4) or hexSymbols[3]
            val blue = (hexSymbols[4] shl 4) or hexSymbols[5]
            val alpha = (hexSymbols[6] shl 4) or hexSymbols[7]
            Color(red, green, blue, alpha)
        }
        else -> null
    }
}

fun Color.toHexString(hexFormat: HexFormat = HexFormat.UpperCase): String {
    val color = this.convert(ColorSpaces.Srgb)

    val r = (color.red * 255).toInt().toByte().toHexString(hexFormat)
    val g = (color.green * 255).toInt().toByte().toHexString(hexFormat)
    val b = (color.blue * 255).toInt().toByte().toHexString(hexFormat)
    val alpha = (color.alpha * 255).toInt().toByte().toHexString(hexFormat)

    return if (color.alpha >= 1)
        "#$r$g$b"
    else
        "#$r$g$b$alpha"
}