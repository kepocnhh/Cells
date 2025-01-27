package test.lwjgl.cells

import sp.kx.math.measure.MutableDoubleMeasure
import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera

internal class MutableEnvironment(
    override val size: IntSize,
    override val measure: MutableDoubleMeasure,
    override val camera: MutableCamera,
) : Environment {
    var stopped: Boolean = false
}
