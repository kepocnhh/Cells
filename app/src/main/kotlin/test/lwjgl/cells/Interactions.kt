package test.lwjgl.cells

import sp.kx.lwjgl.entity.input.KeyboardButton

internal class Interactions(
    private val env: MutableEnvironment,
) {
    fun onPress(button: KeyboardButton) {
        when (button) {
            KeyboardButton.Escape -> env.stopped = true
            KeyboardButton.Minus -> {
                if (env.measure.magnitude > 16.0) env.measure.magnitude -= 8.0
            }
            KeyboardButton.Equal -> {
                if (env.measure.magnitude < 64.0) env.measure.magnitude += 8.0
            }
            else -> Unit
        }
    }
}
