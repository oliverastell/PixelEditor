package com.oliverastell.pixeleditor.util.vector

/* Compares will assume Vec2(x=0, y=1) > Vec2(x=100000, y=0) */
class Region(
    override val start: Vec2i,
    override val endInclusive: Vec2i
) : Iterable<Vec2i>, ClosedRange<Vec2i> {
    val domain: IntRange
        get() = start.x..endInclusive.x

    val range: IntRange
        get() = start.y..endInclusive.y

    val height: Int
        get() = endInclusive.y - start.y + 1

    val length: Int
        get() = endInclusive.x - start.x + 1

    override fun iterator() = object : Iterator<Vec2i> {
        val width = start.x - endInclusive.x + 1
        val height = start.y - endInclusive.y + 1
        val area = width*height

        var index = 0

        override fun hasNext() = index < area

        override fun next(): Vec2i {
            if (!hasNext()) throw NoSuchElementException()

            val vector = Vec2i(start.x + index % width, start.y / width)
            index++

            return vector
        }

    }

    override fun toString() = "$start..$endInclusive"
}
