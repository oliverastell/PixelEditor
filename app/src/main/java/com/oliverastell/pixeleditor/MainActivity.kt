package com.oliverastell.pixeleditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import com.oliverastell.pixeleditor.ui.CanvasComposable
import com.oliverastell.pixeleditor.ui.ToolPanel
import com.oliverastell.pixeleditor.ui.theme.PixelEditorTheme
import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.core.PenTool
import com.oliverastell.pixeleditor.util.vector.Vec2
import kotlin.io.path.createDirectory
import kotlin.io.path.isDirectory
import kotlin.io.path.notExists


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixelEditorTheme {
                val loader by remember {
                    val workingPath = filesDir.toPath()
                    val pluginsPath = workingPath.resolve("plugins")
                    if (pluginsPath.notExists() || !pluginsPath.isDirectory())
                        pluginsPath.createDirectory()

                    val loader = Loader(pluginsPath)
                    loader.editor = Editor(256, 256)

                    mutableStateOf(loader)
                }

                val penTool by remember { mutableStateOf(PenTool(loader)) }

                CanvasComposable(
                    loader.editor!!,
                    onInputDragged = { offset ->
                        penTool.dragMoved(Vec2(offset.x, offset.y))
                    },
                    onInputBegan = { offset ->
                        penTool.dragStart(Vec2(offset.x, offset.y))
                    },
                    onInputEnded = { offset ->
                        penTool.dragEnded(Vec2(offset.x, offset.y))
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                )

                ToolPanel(penTool)
            }
        }
    }
}



