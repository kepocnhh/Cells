package test.lwjgl.cells.entity

import sp.kx.math.measure.Measure
import sp.kx.math.measure.Speed

internal interface Camera {
    val measure: Measure<Double, Double>
    val moveSpeed: Speed
    val backSpeed: Speed
    val offset: CameraOffset
}
