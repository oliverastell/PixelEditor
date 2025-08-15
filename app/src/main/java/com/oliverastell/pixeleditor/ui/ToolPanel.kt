package com.oliverastell.pixeleditor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliverastell.pixeleditor.util.resources.Tool

@Composable
fun ToolPanel(placeholderTool: Tool) {
    GenericPanel(
        menu = {

        },
        subMenu = {
            Column {
                placeholderTool.RenderSettings()
            }
        }
    )
}