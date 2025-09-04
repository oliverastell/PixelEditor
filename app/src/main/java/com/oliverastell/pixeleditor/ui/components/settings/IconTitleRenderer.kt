package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import com.oliverastell.pixeleditor.util.EditorAppState

@Composable
fun IconTitleRenderer(appState: EditorAppState, painter: Painter, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(appState.sizing.baseSettingHeight)
    ) {
        Box(modifier = Modifier.fillMaxHeight().aspectRatio(1f)) {
            Image(
                painter,
                contentDescription = title,
                modifier = Modifier
                    .size(appState.sizing.baseSettingIconHeight)
                    .align(Alignment.Center)
            )
        }

        Text(title,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    }
}