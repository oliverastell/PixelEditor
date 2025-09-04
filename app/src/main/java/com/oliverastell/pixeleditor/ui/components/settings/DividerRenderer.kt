package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oliverastell.pixeleditor.util.EditorAppState

@Composable
fun DividerRenderer(appState: EditorAppState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(appState.sizing.baseSettingHeight)
    ) {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.Center),
            thickness = appState.sizing.s
        )
    }
}