package com.oliverastell.pixeleditor.util.transactions

import androidx.collection.intListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.collections.BitMatrix
import com.oliverastell.pixeleditor.util.vector.Vec2

class PaintingTransaction(override val editor: Editor) : Transaction {
    val changes: HashMap<Vec2, Color> = hashMapOf()

    fun setColorAt() {

    }
}