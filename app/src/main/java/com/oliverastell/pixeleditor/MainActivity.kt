package com.oliverastell.pixeleditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.oliverastell.pixeleditor.ui.EditorView
import com.oliverastell.pixeleditor.ui.theme.Sizing
import com.oliverastell.pixeleditor.ui.theme.PixelEditorTheme
import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.FileSystem
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.plugin.core.CoreIdentifiers


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixelEditorTheme {
                val appState by remember {
                    val workingPath = filesDir.toPath()
                    val loader = Loader()
                    val editor = Editor(256, 256)

                    val appState = AppState(
                        sizing = Sizing.default,
                        loader = loader,
                        fileSystem = FileSystem(workingPath),
                        editor = editor
                    )
                    loader.defaultInit(appState)

                    val pen = loader.getTool(CoreIdentifiers.PenTool)!!
                    editor.activeTool = pen
                    editor.tools.add(pen.identifier)

                    val solidColor = loader.getTexture(CoreIdentifiers.SolidColorTexture)!!
                    editor.textures.add(solidColor.identifier)

                    val gridMask = loader.getTexture(CoreIdentifiers.GridMask)!!
                    editor.textures.add(gridMask.identifier)

                    mutableStateOf(appState)
                }

                EditorView(appState.editorAppState())
            }
        }
    }
}



