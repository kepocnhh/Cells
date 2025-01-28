package test.lwjgl.cells

import test.lwjgl.cells.entity.Camera
import test.lwjgl.cells.entity.IntSize

internal interface Environment {
    val size: IntSize
    val camera: Camera
}
