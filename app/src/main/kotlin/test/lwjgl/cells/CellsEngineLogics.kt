package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.Point
import sp.kx.math.measure.frequency
import sp.kx.math.measure.measureOf

internal class CellsEngineLogics(
    private val engine: Engine,
) : EngineLogics {
    private lateinit var ses: Unit
    override val inputCallback = object : EngineInputCallback {
        override fun onKeyboardButton(button: KeyboardButton, isPressed: Boolean) {
            if (!isPressed) return
            when (button) {
                KeyboardButton.Escape -> ses = Unit
                else -> Unit
            }
        }
    }
    private val measure = measureOf(24.0)

    override fun onRender(canvas: Canvas) {
        val fps = engine.property.time.frequency()
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = 1.0,
            text = String.format("%6.2f", fps),
            pointTopLeft = Point.Center,
            measure = measure,
        )
    }

    override fun shouldEngineStop(): Boolean {
        return ::ses.isInitialized
    }
}
