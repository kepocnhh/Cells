package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.copy
import sp.kx.math.Offset
import sp.kx.math.Point
import sp.kx.math.centerPoint
import sp.kx.math.copy
import sp.kx.math.div
import sp.kx.math.vectorOf

internal class Renders(
    private val engine: Engine,
    private val env: Environment,
) {
    private fun onRenderOffset(canvas: Canvas, offset: Offset, color: Color) {
        val measure = env.camera.measure
        val ps = engine.property.pictureSize / measure
        for (it in 2..ps.width.toInt()) {
            val dX = it - offset.dX
            val value = java.lang.Math.floor(dX).toInt()
            val x = offset.dX + value
            canvas.texts.draw(
                color = color,
                fontHeight = 0.75,
                pointTopLeft = Point.Center.copy(x = x),
                text = "$value",
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = vectorOf(x, 0.0, x, ps.height),
                lineWidth = 0.05,
                measure = measure,
            )
        }
        for (it in 2..ps.height.toInt()) {
            val dY = it - offset.dY
            val value = java.lang.Math.floor(dY).toInt()
            val y = offset.dY + value
            canvas.texts.draw(
                color = color,
                fontHeight = 0.75,
                pointTopLeft = Point.Center.copy(y = y),
                text = "$value",
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = vectorOf(0.0, y, ps.width, y),
                lineWidth = 0.05,
                measure = measure,
            )
        }
    }

    fun onRender(canvas: Canvas) {
        val w = env.size.width
        val h = env.size.height
        val offset = env.camera.offset
        val measure = env.camera.measure
        onRenderOffset(canvas = canvas, offset = offset, color = Color.Gray.copy(alpha = 0.5f))
        for (x in 0..w) {
            canvas.vectors.draw(
                color = Color.Gray,
                vector = vectorOf(x, 0, x, h),
                lineWidth = 0.1,
                offset = offset,
                measure = measure,
            )
        }
        for (y in 0..h) {
            canvas.vectors.draw(
                color = Color.Gray,
                vector = vectorOf(0, y, w, y),
                lineWidth = 0.1,
                offset = offset,
                measure = measure,
            )
        }
        canvas.polygons.drawCircle(
            color = Color.Green,
            pointCenter = engine.property.pictureSize.centerPoint(),
            radius = 4.0,
            edgeCount = 4,
        )
    }
}
