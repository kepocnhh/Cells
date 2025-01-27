package test.lwjgl.cells.entity

import sp.kx.math.MutableOffset
import sp.kx.math.measure.Speed

internal class MutableCamera(
    override val speed: Speed,
    override val offset: MutableOffset,
) : Camera
