package com.oliverastell.pixeleditor.util.resources

import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader

interface Resource {
    val loader: Loader
    val name: String
    val identifier: Identifier
}