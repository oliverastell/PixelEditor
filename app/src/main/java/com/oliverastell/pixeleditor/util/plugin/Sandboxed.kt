package com.oliverastell.pixeleditor.util.plugin

/**
 * Allows this method to be accessed from dynamic objects
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Sandboxed()