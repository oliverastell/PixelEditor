package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun VerticalScaffold(
    modifier: Modifier = Modifier,
    topInset: @Composable () -> Unit = {},
    bottomInset: @Composable () -> Unit = {},
    bottomNoInset: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topInset,
        bottomBar = bottomInset,
        modifier = modifier
    ) { paddingValues ->
        content()
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(Modifier.weight(1f))
            bottomNoInset()
        }
    }
}