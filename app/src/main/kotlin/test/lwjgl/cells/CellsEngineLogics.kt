package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.Point
import sp.kx.math.measure.frequency

internal class CellsEngineLogics(
    private val engine: Engine,
) : EngineLogics {
    private val env = MutableEnvironment()
    private val calculations = Calculations(engine = engine)
    private val renders = Renders(engine = engine)
    private val interactions = Interactions(env = env)
    override val inputCallback = object : EngineInputCallback {
        override fun onKeyboardButton(button: KeyboardButton, isPressed: Boolean) {
            if (!isPressed) interactions.onPress(button = button)
        }
    }

    override fun onRender(canvas: Canvas) {
        val fps = engine.property.time.frequency()
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = 24.0,
            text = String.format("%6.2f", fps),
            pointTopLeft = Point.Center,
        )
        calculations.onPreRender()
        renders.onRender(canvas = canvas)
    }

    override fun shouldEngineStop(): Boolean {
        return env.ses
    }
}
