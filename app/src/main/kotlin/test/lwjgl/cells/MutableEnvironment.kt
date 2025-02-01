package test.lwjgl.cells

import sp.kx.math.Size
import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera
import test.lwjgl.cells.entity.MutableIntPoint

internal class MutableEnvironment(
    override val grid: IntSize,
    override val cellSize: Size,
    override val selected: MutableIntPoint,
    override val camera: MutableCamera,
    override var offset: Boolean,
    override var debug: Boolean,
) : Environment {
    override var fps = Double.NaN
    var stopped: Boolean = false
}
