package test.lwjgl.cells

import sp.kx.math.Size
import test.lwjgl.cells.entity.Camera
import test.lwjgl.cells.entity.IntPoint
import test.lwjgl.cells.entity.IntSize

internal interface Environment {
    val grid: IntSize
    val cellSize: Size
    val selected: IntPoint
    val focused: IntPoint
    val camera: Camera
    val offset: Boolean
    val fps: Double
    val debug: Boolean
}
