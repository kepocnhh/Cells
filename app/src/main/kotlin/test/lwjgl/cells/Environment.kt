package test.lwjgl.cells

import sp.kx.math.measure.Measure
import test.lwjgl.cells.entity.Camera
import test.lwjgl.cells.entity.IntSize

internal interface Environment {
    val size: IntSize
    val measure: Measure<Double, Double>
    val camera: Camera
}
