package com.oliverastell.pixeleditor.ui.settings

import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliverastell.pixeleditor.util.Identifier
import kotlin.math.exp

@Composable
inline fun <reified Expected> BadTypeErrorSettingRenderer(got: Class<*>) {
    ErrorSettingRenderer("Cannot render ${got.simpleName} as ${Expected::class.java.simpleName}")
}

@Composable
fun UnknownErrorSettingRenderer(identifier: Identifier) {
    ErrorSettingRenderer("No setting with identifier $identifier")
}

@Composable
fun ErrorSettingRenderer(errorMessage: String) {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        Text(
            errorMessage,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.errorContainer)
        )
    }
}