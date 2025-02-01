package test.lwjgl.cells.entity

import sp.kx.math.measure.MutableDoubleMeasure
import sp.kx.math.measure.Speed

internal class MutableCamera(
    override val measure: MutableDoubleMeasure,
    override val moveSpeed: Speed,
    override val backSpeed: Speed,
    override val offset: MutableCameraOffset,
) : Camera
