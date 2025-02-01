package test.lwjgl.cells.entity

import sp.kx.math.MutableOffset
import sp.kx.math.Offset

internal class MutableCameraOffset(
    override val expected: MutableOffset,
    override val actual: MutableOffset,
) : CameraOffset {
    fun set(other: Offset) {
        actual.set(other = other)
        expected.set(other = actual)
    }

    fun set(dX: Double, dY: Double) {
        actual.set(dX = dX, dY = dY)
        expected.set(other = actual)
    }

    fun add(dX: Double, dY: Double) {
        actual.add(dX = dX, dY = dY)
        expected.set(other = actual)
    }
}
