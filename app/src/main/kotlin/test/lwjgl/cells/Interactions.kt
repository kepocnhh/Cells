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
                if (env.camera.measure.magnitude > 16.0) {
                    setMagnitude(magnitude = env.camera.measure.magnitude - 8.0)
                }
            }
            KeyboardButton.Equal -> {
                if (env.camera.measure.magnitude < 64.0) {
                    setMagnitude(magnitude = env.camera.measure.magnitude + 8.0)
                }
            }
            else -> Unit
        }
    }
}
