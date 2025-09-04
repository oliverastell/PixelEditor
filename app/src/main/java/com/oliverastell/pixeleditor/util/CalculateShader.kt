package com.oliverastell.pixeleditor.util

import android.graphics.BitmapShader
import android.graphics.BlendMode
import android.graphics.ComposeShader
import android.graphics.RuntimeShader
import android.graphics.Shader
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.oliverastell.pixeleditor.common.toInt
import com.oliverastell.pixeleditor.util.resources.Texture

val transparentShader: Shader = BitmapShader(
    createBitmap(1, 1).apply { set(0, 0, Color(0, 0, 0, 0).toInt()) },
    Shader.TileMode.REPEAT,
    Shader.TileMode.REPEAT
)

val shaderMerger = """
//    uniform shader a;
//    uniform shader b;
//
//    half4 main(in vec2 fragCoord) {
//        vec4 colorA = a.eval(fragCoord);
//        vec4 colorB = b.eval(fragCoord);
//
//        return half4(
//            colorA.r * colorB.r,
//            colorA.g * colorB.g,
//            colorA.b * colorB.b,
//            colorA.a * colorB.a
//        );
//    }
    half4 main(in vec2 fragCoord) {
        return vec4(1)
    }

""".trimIndent()

fun calculateBrushShader(appState: EditorAppState, textures: List<Texture>, size: IntSize): Shader {
    if (textures.isEmpty())
        return transparentShader

    var shader = textures.first().getShader(appState, size)

    for (textureIdx in 1..<textures.size) {
        val shaderA = shader
        val shaderB = textures[textureIdx].getShader(appState, size)

//        val runtime =
//        val runtime = RuntimeShader(shaderMerger)
//        runtime.setInputShader("a", shaderA)
//        runtime.setInputShader("b", shaderB)

        shader = ComposeShader(shaderA, shaderB, BlendMode.MODULATE)
    }

    return shader

}