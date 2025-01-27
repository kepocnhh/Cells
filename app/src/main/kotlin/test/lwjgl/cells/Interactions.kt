package test.lwjgl.cells

import sp.kx.lwjgl.entity.input.KeyboardButton

internal class Interactions(
    private val env: MutableEnvironment,
) {
    fun onPress(button: KeyboardButton) {
        when (button) {
            KeyboardButton.Escape -> env.ses = true
            else -> Unit
        }
    }
}
