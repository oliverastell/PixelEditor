package com.oliverastell.pixeleditor.util

fun nothing(action: () -> Unit): Nothing {
    action()
    throw RuntimeException("Nothing")
}