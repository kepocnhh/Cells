package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.div

internal class Interactions(
    private val engine: Engine,
    private val env: MutableEnvironment,
) {
    private fun setMagnitude(magnitude: Double) {
        val ps = engine.property.pictureSize
        val op = ps.div(env.camera.measure)
        val dw = op.width / 2 - env.camera.offset.dX
        val dh = op.height / 2 - env.camera.offset.dY
        env.camera.measure.magnitude = magnitude
        val np = ps.div(env.camera.measure)
        env.camera.offset.set(
            dX = np.width / 2 - dw,
            dY = np.height / 2 - dh,
        )
    }

    fun onPress(button: KeyboardButton) {
        when (button) {
            KeyboardButton.Escape -> env.stopped = true
            KeyboardButton.Minus -> {
                if (env.camera.measure.magnitude > 8.0) {
                    setMagnitude(magnitude = env.camera.measure.magnitude - 8.0)
                }
            }
            KeyboardButton.Equal -> {
                if (env.camera.measure.magnitude < 64.0) {
                    setMagnitude(magnitude = env.camera.measure.magnitude + 8.0)
                }
            }
            KeyboardButton.O -> {
                env.offset = !env.offset
            }
            KeyboardButton.P -> {
                env.debug = !env.debug
            }
            KeyboardButton.A -> {
                if (engine.input.keyboard.isPressed(KeyboardButton.Shift)) {
                    if (env.selected.x > 0) env.selected.x -= 1
                }
            }
            KeyboardButton.D -> {
                if (engine.input.keyboard.isPressed(KeyboardButton.Shift)) {
                    if (env.selected.x < env.grid.width - 1) {
                        env.selected.x += 1
                    }
                }
            }
            KeyboardButton.W -> {
                if (engine.input.keyboard.isPressed(KeyboardButton.Shift)) {
                    if (env.selected.y > 0) env.selected.y -= 1
                }
            }
            KeyboardButton.S -> {
                if (engine.input.keyboard.isPressed(KeyboardButton.Shift)) {
                    if (env.selected.y < env.grid.height - 1) {
                        env.selected.y += 1
                    }
                }
            }
            KeyboardButton.Space -> {
                if (env.focused != env.selected) {
                    env.selected.set(other = env.focused)
                }
            }
            else -> Unit
        }
    }
}
