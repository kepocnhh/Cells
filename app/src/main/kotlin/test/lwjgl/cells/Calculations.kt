package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.input.Keyboard
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.Offset
import sp.kx.math.angleOf
import sp.kx.math.div
import sp.kx.math.isEmpty
import sp.kx.math.measure.diff
import sp.kx.math.measure.frequency
import sp.kx.math.offsetOf
import sp.kx.math.radians

internal class Calculations(
    private val engine: Engine,
    private val env: MutableEnvironment,
) {
    private fun getCameraOffset(): Offset {
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
        val offset = getCameraOffset()
        if (!offset.isEmpty()) {
            val length = env.camera.speed.length(engine.property.time.diff())
            val angle = angleOf(offset).radians()
            env.camera.offset.add(
                dX = length * kotlin.math.cos(angle),
                dY = length * kotlin.math.sin(angle),
            )
        }
        //
        val ps = engine.property.pictureSize
        val psu = ps / env.camera.measure
        val center = offsetOf(
            dX = psu.width / 2 - env.camera.offset.dX,
            dY = psu.height / 2 - env.camera.offset.dY,
        )
        env.focused.set(
            x = java.lang.Math.floor(center.dX / env.cellSize.width).toInt(),
            y = java.lang.Math.floor(center.dY / env.cellSize.height).toInt(),
        )
    }
}
