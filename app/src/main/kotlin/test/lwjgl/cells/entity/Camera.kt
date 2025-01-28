package test.lwjgl.cells.entity

import sp.kx.math.Offset
import sp.kx.math.measure.Measure
import sp.kx.math.measure.Speed

internal interface Camera {
    val measure: Measure<Double, Double>
    val speed: Speed
    val offset: Offset
}
