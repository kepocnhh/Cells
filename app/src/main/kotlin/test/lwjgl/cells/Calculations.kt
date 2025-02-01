package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.input.Keyboard
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.Offset
import sp.kx.math.Point
import sp.kx.math.angleOf
import sp.kx.math.copy
import sp.kx.math.distanceOf
import sp.kx.math.div
import sp.kx.math.isEmpty
import sp.kx.math.measure.diff
import sp.kx.math.measure.frequency
import sp.kx.math.offsetOf
import sp.kx.math.radians
import sp.kx.math.plus

internal class Calculations(
    private val engine: Engine,
    private val env: MutableEnvironment,
) {
    private fun getOffset(): Offset {
        val keyboard = engine.input.keyboard
        if (keyboard.isPressed(KeyboardButton.Shift)) return Offset.Empty
        val offset = MutableOffset(0.0, 0.0)
        val left = keyboard.isPressed(KeyboardButton.A)
        if (keyboard.isPressed(KeyboardButton.D)) {
            if (!left) offset.dX = -1.0
        } else if (left) {
            offset.dX = 1.0
        }
        val top = keyboard.isPressed(KeyboardButton.W)
        if (keyboard.isPressed(KeyboardButton.S)) {
            if (!top) offset.dY = -1.0
        } else if (top) {
            offset.dY = 1.0
        }
        return offset
    }

    fun onPreRender() {
        env.fps = engine.property.time.frequency()
        val moved = getOffset()
        if (moved.isEmpty()) {
            val e = env.camera.offset.expected.copy()
            val a = env.camera.offset.actual.copy()
            if (e != a) {
                val md = distanceOf(a, e)
                val length = env.camera.backSpeed.length(engine.property.time.diff())
                if (length < md) {
                    val angle = angleOf(a, e).radians()
                    env.camera.offset.actual.add(
                        dX = length * kotlin.math.cos(angle),
                        dY = length * kotlin.math.sin(angle),
                    )
                } else {
                    env.camera.offset.set(other = e)
                }
            }
        } else {
            val length = env.camera.moveSpeed.length(engine.property.time.diff())
            val angle = angleOf(moved).radians()
            env.camera.offset.add(
                dX = length * kotlin.math.cos(angle),
                dY = length * kotlin.math.sin(angle),
            )
        }
        //
        val ps = engine.property.pictureSize
        val psu = ps / env.camera.measure
        val offset = env.camera.offset.actual
        val center = offsetOf(
            dX = psu.width / 2 - offset.dX,
            dY = psu.height / 2 - offset.dY,
        )
        env.focused.set(
            x = java.lang.Math.floor(center.dX / env.cellSize.width).toInt(),
            y = java.lang.Math.floor(center.dY / env.cellSize.height).toInt(),
        )
    }
}
