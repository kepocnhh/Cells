package test.lwjgl.cells.entity

import sp.kx.math.MutableOffset
import sp.kx.math.measure.MutableDoubleMeasure
import sp.kx.math.measure.Speed

internal class MutableCamera(
    override val measure: MutableDoubleMeasure,
    override val speed: Speed,
    override val offset: MutableOffset,
) : Camera
