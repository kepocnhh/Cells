package test.lwjgl.cells

import test.lwjgl.cells.entity.Camera
import test.lwjgl.cells.entity.IntPoint
import test.lwjgl.cells.entity.IntSize

internal interface Environment {
    val size: IntSize
    val selected: IntPoint
    val camera: Camera
    val offset: Boolean
    val fps: Double
    val debug: Boolean
}
