package com.oliverastell.pixeleditor.util.transactions

import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2

class PaintingTransaction(override val editor: Editor) : Transaction {
    val changes: HashMap<Vec2, Color> = hashMapOf()

    fun setColorAt() {

    }
}