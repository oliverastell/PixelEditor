package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        color = MaterialTheme.colorScheme.onErrorContainer,
        modifier = modifier.background(MaterialTheme.colorScheme.errorContainer)
    )
}