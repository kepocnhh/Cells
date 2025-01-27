package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.input.Keyboard
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.Offset
import sp.kx.math.angleOf
import sp.kx.math.eq
import sp.kx.math.isEmpty
import sp.kx.math.measure.diff
import sp.kx.math.radians

internal class Calculations(
    private val engine: Engine,
    private val env: MutableEnvironment,
) {
    private fun getCameraOffset(keyboard: Keyboard): Offset {
        val offset = MutableOffset(0.0, 0.0)
        val left = keyboard.isPressed(KeyboardButton.A)
        if (keyboard.isPressed(KeyboardButton.D)) {
            if (!left) offset.dX = 1.0
        } else if (left) {
            offset.dX = -1.0
        }
        val top = keyboard.isPressed(KeyboardButton.W)
        if (keyboard.isPressed(KeyboardButton.S)) {
            if (!top) offset.dY = 1.0
        } else if (top) {
            offset.dY = -1.0
        }
        return offset
    }

    fun onPreRender() {
        val offset = getCameraOffset(keyboard = engine.input.keyboard)
        if (!offset.isEmpty()) {
            val length = env.camera.speed.length(engine.property.time.diff())
            val angle = angleOf(offset).radians()
            env.camera.offset.add(
                dX = length * kotlin.math.cos(angle),
                dY = length * kotlin.math.sin(angle),
            )
        }
    }
}
