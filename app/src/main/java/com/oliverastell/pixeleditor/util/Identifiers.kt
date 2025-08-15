package com.oliverastell.pixeleditor.util

import com.oliverastell.pixeleditor.util.plugin.MalformedIdentifier

@ConsistentCopyVisibility
data class Namespace private constructor(val name: String) {
    override fun toString() = name

    companion object {
        fun fromString(string: String) = Namespace(string.lowercase())
    }
}
@ConsistentCopyVisibility
data class IdentifierPath private constructor(val segments: List<String>) {
    constructor() : this(listOf())

    fun withoutBlank() = fromSegments(segments.filter { it.isNotBlank() })

    fun withSuffix(prefix: IdentifierPath) = fromSegments(segments + prefix.segments)
    fun withSuffix(suffix: String) = withSuffix(IdentifierPath(listOf(suffix)))

    fun withPrefix(prefix: IdentifierPath) = fromSegments(prefix.segments + segments)
    fun withPrefix(prefix: String) = withPrefix(IdentifierPath(listOf(prefix)))

    override fun toString() = segments.joinToString(SEPARATOR)

    companion object {
        const val SEPARATOR = "."
        val ROOT = IdentifierPath(listOf())

        fun fromString(path: String) = fromSegments(path.split(SEPARATOR))
        fun fromSegments(segments: List<String>) = IdentifierPath(segments.filter { it.isNotBlank() })
    }
}

//interface Identifier {
//
//}

class Identifier(
    val namespace: Namespace,
    val path: IdentifierPath
) {
    constructor(namespace: String, path: IdentifierPath) : this(Namespace.fromString(namespace), path)
    constructor(namespace: String, path: String) : this(Namespace.fromString(namespace),
        IdentifierPath.fromString(path)
    )

    override fun toString() = "$namespace$SEPARATOR$path"

    companion object {
        const val SEPARATOR = ":"

        fun fromString(identifier: String): Identifier {
            val segments = identifier.split(SEPARATOR)

            if (segments.size != 2)
                throw MalformedIdentifier("{namespace}:{path}")

            val namespace = Namespace.fromString(segments[0])
            val path = IdentifierPath.fromString(segments[1])

            return Identifier(namespace, path)
        }

        fun fromString(defaultNamespace: String, identifier: String): Identifier {
            val segments = identifier.split(SEPARATOR)

            return when (segments.size) {
                1 -> {
                    val namespace = Namespace.fromString(defaultNamespace)
                    val path = IdentifierPath.fromString(segments[0])

                    Identifier(namespace, path)
                }
                2 -> {
                    val namespace = Namespace.fromString(segments[0])
                    val path = IdentifierPath.fromString(segments[1])

                    Identifier(namespace, path)
                }
                else -> throw MalformedIdentifier("{namespace?}:{path}")
            }
        }

        fun fromString(defaultNamespace: Namespace, identifier: String) = fromString(defaultNamespace.name, identifier)
    }
}
