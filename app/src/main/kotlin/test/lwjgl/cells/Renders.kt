package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.colorOf
import sp.kx.lwjgl.entity.copy
import sp.kx.math.Offset
import sp.kx.math.Point
import sp.kx.math.Size
import sp.kx.math.copy
import sp.kx.math.div
import sp.kx.math.offsetOf
import sp.kx.math.pointOf
import sp.kx.math.sizeOf
import sp.kx.math.toVector
import sp.kx.math.vectorOf

internal class Renders(
    private val engine: Engine,
    private val env: Environment,
) {
    private fun onRenderOffset(canvas: Canvas) {
        val ps = engine.property.pictureSize
        canvas.vectors.draw(
            color = Color.Green.copy(alpha = 0.5f),
            vector = pointOf(x = ps.width / 2, y = 0.0).toVector(Offset.Empty.copy(dY = ps.height)),
        )
        canvas.vectors.draw(
            color = Color.Green.copy(alpha = 0.5f),
            vector = pointOf(x = 0.0, y = ps.height / 2).toVector(Offset.Empty.copy(dX = ps.width)),
        )
        val offset = env.camera.offset
        val measure = env.camera.measure
        val psu = ps / measure
        val center = offsetOf(
            dX = psu.width / 2 - offset.dX,
            dY = psu.height / 2 - offset.dY,
        )
        for (it in 2..psu.width.toInt()) {
            val dX = it - offset.dX
            val value = java.lang.Math.floor(dX).toInt()
            val x = offset.dX + value
            val textColor = when {
                value == java.lang.Math.floor(center.dX).toInt() ||
                value == java.lang.Math.ceil(center.dX).toInt()-> Color.Green.copy(alpha = 0.5f)
                value == 0 -> Color.Yellow.copy(alpha = 0.5f)
                value % 2 == 0 -> Color.Gray
                else -> Color.Gray.copy(alpha = 0.5f)
            }
            val color = when {
                value == 0 -> Color.Yellow.copy(alpha = 0.5f)
                value % 2 == 0 -> Color.Gray
                else -> Color.Gray.copy(alpha = 0.5f)
            }
            canvas.texts.draw(
                color = textColor,
                fontHeight = 0.75,
                pointTopLeft = Point.Center.copy(x = x),
                text = "$value",
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = vectorOf(x, 0.0, x, psu.height),
                measure = measure,
            )
        }
        for (it in 2..psu.height.toInt()) {
            val dY = it - offset.dY
            val value = java.lang.Math.floor(dY).toInt()
            val y = offset.dY + value
            val textColor = when {
                value == java.lang.Math.floor(center.dY).toInt() ||
                value == java.lang.Math.ceil(center.dY).toInt()-> Color.Green.copy(alpha = 0.5f)
                value == 0 -> Color.Yellow.copy(alpha = 0.5f)
                value % 2 == 0 -> Color.Gray
                else -> Color.Gray.copy(alpha = 0.5f)
            }
            val color = when {
                value == 0 -> Color.Yellow.copy(alpha = 0.5f)
                value % 2 == 0 -> Color.Gray.copy(alpha = 0.75f)
                else -> Color.Gray.copy(alpha = 0.5f)
            }
            canvas.texts.draw(
                color = textColor,
                fontHeight = 0.75,
                pointTopLeft = Point.Center.copy(y = y),
                text = "$value",
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = vectorOf(0.0, y, psu.width, y),
                measure = measure,
            )
        }
    }

    private fun onRenderDebug(canvas: Canvas) {
        val ps = engine.property.pictureSize
        val fontHeight = 24.0
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = fontHeight,
            text = String.format("%6.2f", env.fps),
            pointTopLeft = pointOf(x = ps.width - 128.0, y = ps.height - fontHeight * 2),
        )
        val psu = ps / env.camera.measure
        listOf(
            String.format("m: %6.2f", env.camera.measure.magnitude),
            String.format("o: %+6.2f %+6.2f", env.camera.offset.dX, env.camera.offset.dY),
            String.format("c: %+6.2f %+6.2f", psu.width / 2 - env.camera.offset.dX, psu.height / 2 - env.camera.offset.dY),
            String.format("s: %2d %2d", env.selected.x, env.selected.y),
            String.format("f: %2d %2d", env.focused.x, env.focused.y),
        ).forEachIndexed { index, text ->
            canvas.texts.draw(
                color = Color.Green,
                fontHeight = fontHeight,
                text = text,
                pointTopLeft = pointOf(x = fontHeight * 2, y = ps.height - fontHeight * (index + 2)),
            )
        }
    }

    fun onRender(canvas: Canvas) {
        if (env.offset) onRenderOffset(canvas = canvas)
        //
        val offset = env.camera.offset
        val measure = env.camera.measure
        for (x in 0..env.grid.width) {
            canvas.vectors.draw(
                color = colorOf(0xff1565C0),
                vector = Point.Center.copy(x = x * env.cellSize.width)
                    .toVector(Offset.Empty.copy(dY = env.grid.height * env.cellSize.height)),
                offset = offset,
                measure = measure,
            )
        }
        for (y in 0..env.grid.height) {
            canvas.vectors.draw(
                color = colorOf(0xff1565C0),
                vector = Point.Center.copy(y = y * env.cellSize.height)
                    .toVector(Offset.Empty.copy(dX = env.grid.width * env.cellSize.width)),
                offset = offset,
                measure = measure,
            )
        }
        if (env.focused != env.selected) canvas.polygons.drawRectangle(
            color = Color.Gray,
            pointTopLeft = pointOf(x = env.focused.x * env.cellSize.width, y = env.focused.y * env.cellSize.height),
            size = env.cellSize,
            lineWidth = 0.1,
            offset = offset,
            measure = measure,
        )
        canvas.polygons.drawRectangle(
            color = Color.White,
            pointTopLeft = pointOf(x = env.selected.x * env.cellSize.width, y = env.selected.y * env.cellSize.height),
            size = env.cellSize,
            lineWidth = 0.1,
            offset = offset,
            measure = measure,
        )
        //
        if (env.debug) onRenderDebug(canvas = canvas)
    }
}
