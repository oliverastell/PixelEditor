package com.oliverastell.pixeleditor.util.resources

import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader

interface Resource {
//    val appState: AppState
    val name: String
    val identifier: Identifier
}