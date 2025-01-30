package test.lwjgl.cells

import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera

internal class MutableEnvironment(
    override val size: IntSize,
    override val camera: MutableCamera,
    override var offset: Boolean,
    override var debug: Boolean,
) : Environment {
    override var fps = Double.NaN
    var stopped: Boolean = false
}
