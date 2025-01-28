package test.lwjgl.cells

import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera

internal class MutableEnvironment(
    override val size: IntSize,
    override val camera: MutableCamera,
) : Environment {
    var stopped: Boolean = false
}
