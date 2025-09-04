package com.oliverastell.pixeleditor.util.selection

import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2
import kotlin.math.PI
import kotlin.random.Random

object Shapes {
    fun rectangle(pointA: Vec2, pointB: Vec2): PolygonRegion = PolygonRegion(
        Vec2(pointA.x, pointA.y),
        Vec2(pointA.x, pointB.y),
        Vec2(pointB.x, pointB.y),
        Vec2(pointB.x, pointA.y)
    )

    fun line(pointA: Vec2, pointB: Vec2, radius: Double): PolygonRegion {
        val lineVector = pointB - pointA
        val angle = lineVector.angle

        val leftAngle = angle - PI/2
        val rightAngle = angle + PI/2

        val leftVector = Vec2.fromAngle(leftAngle) * radius
        val rightVector = Vec2.fromAngle(rightAngle) * radius

        return PolygonRegion(
            pointA + leftVector,
            pointA + rightVector,
            pointB + rightVector,
            pointB + leftVector
        )
    }

    fun square(centroid: Vec2, radius: Double): PolygonRegion = rectangle(
        Vec2(centroid.x-radius, centroid.y-radius),
        Vec2(centroid.x+radius, centroid.y+radius)
    )

    fun circle(centroid: Vec2, radius: Double, vertexCount: Int = (2*radius*PI).toInt()): PolygonRegion {
        val separation = PI*2 / vertexCount

        var currentAngle = 0.0
        val points = List(vertexCount) {
            currentAngle += separation
            centroid + Vec2.fromAngle(currentAngle) * radius
        }

        return PolygonRegion(points)
    }

    fun randomPolygon(width: Double, height: Double, seed: Int, vertexCount: Int): PolygonRegion {
        val random = Random(seed)
        val list = mutableListOf<Vec2>()

        for (i in 0..<vertexCount) {
            list.add(Vec2(
                random.nextDouble(width),
                random.nextDouble(height)
            ))
        }

        return PolygonRegion(list.toList())
    }
}