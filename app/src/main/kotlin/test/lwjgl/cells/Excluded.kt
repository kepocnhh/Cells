package test.lwjgl.cells

import sp.kx.math.Offset
import sp.kx.math.Point
import sp.kx.math.distanceOf
import sp.kx.math.angleOf

@Deprecated("sp.kx.math.distanceOf")
internal fun distanceOf(point: Point, offset: Offset): Double {
    return distanceOf(
        aX = point.x,
        aY = point.y,
        bX = point.x + offset.dX,
        bY = point.y + offset.dY,
    )
}

@Deprecated("sp.kx.math.distanceOf")
internal fun distanceOf(dA: Offset, dB: Offset): Double {
    return distanceOf(
        aX = dA.dX,
        aY = dA.dY,
        bX = dB.dX,
        bY = dB.dY,
    )
}

@Deprecated("sp.kx.math.angleOf")
internal fun angleOf(dA: Offset, dB: Offset): Double {
    // todo
    // angleOf(Point, Offset) = angleOf(p.x, p.y, p.x + o.dX, p.y + o.dY)
    // todo
    return angleOf(
        aX = dA.dX,
        aY = dA.dY,
        bX = dB.dX,
        bY = dB.dY,
    )
}
