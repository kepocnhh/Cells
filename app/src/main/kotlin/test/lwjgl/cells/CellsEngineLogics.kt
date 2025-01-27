package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.div
import sp.kx.math.measure.MutableDoubleMeasure
import sp.kx.math.measure.frequency
import sp.kx.math.measure.speedOf
import sp.kx.math.pointOf
import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera

internal class CellsEngineLogics(
    private val engine: Engine,
) : EngineLogics {
    private val env = engine.let {
        val measure = MutableDoubleMeasure(24.0)
        val ps = it.property.pictureSize / measure
        val size = IntSize(
            width = 8,
            height = 8,
        )
        MutableEnvironment(
            size = size,
            measure = measure,
            camera = MutableCamera(
                speed = speedOf(1.0),
                offset = MutableOffset(
                    dX = (ps.width - size.width) / 2,
                    dY = (ps.height - size.height) / 2,
                ),
            )
        )
    }
    private val calculations = Calculations(engine = engine)
    private val renders = Renders(
        env = env,
        engine = engine,
    )
    private val interactions = Interactions(env = env)
    override val inputCallback = object : EngineInputCallback {
        override fun onKeyboardButton(button: KeyboardButton, isPressed: Boolean) {
            if (!isPressed) interactions.onPress(button = button)
        }
    }

    override fun onRender(canvas: Canvas) {
        calculations.onPreRender()
        renders.onRender(canvas = canvas)
        //
        val fps = engine.property.time.frequency()
        val ps = engine.property.pictureSize
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = 24.0,
            text = String.format("%6.2f", fps),
            pointTopLeft = pointOf(x = ps.width - 128.0, y = ps.height - 48.0),
        )
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = 24.0,
            text = String.format("%6.2f", env.measure.magnitude),
            pointTopLeft = pointOf(x = 48.0, y = ps.height - 48.0),
        )
    }

    override fun shouldEngineStop(): Boolean {
        return env.stopped
    }
}
