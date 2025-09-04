package com.oliverastell.pixeleditor.ui.painter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.ColorPainter

@get:Composable
val errorPainter
    get() = ColorPainter(MaterialTheme.colorScheme.errorContainer)