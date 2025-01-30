package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.Point
import sp.kx.math.angleOf
import sp.kx.math.center
import sp.kx.math.centerPoint
import sp.kx.math.distanceOf
import sp.kx.math.div
import sp.kx.math.measure.MutableDoubleMeasure
import sp.kx.math.measure.diff
import sp.kx.math.measure.frequency
import sp.kx.math.measure.speedOf
import sp.kx.math.minus
import sp.kx.math.moved
import sp.kx.math.plus
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
            camera = MutableCamera(
                measure = measure,
                speed = speedOf(8.0),
                offset = MutableOffset(
                    dX = (ps.width - size.width) / 2,
                    dY = (ps.height - size.height) / 2,
                ),
            ),
            offset = true,
        )
    }
    private val calculations = Calculations(
        engine = engine,
        env = env,
    )
    private val renders = Renders(
        engine = engine,
        env = env,
    )
    private val interactions = Interactions(
        engine = engine,
        env = env,
    )
    override val inputCallback = object : EngineInputCallback {
        override fun onKeyboardButton(button: KeyboardButton, isPressed: Boolean) {
            if (!isPressed) interactions.onPress(button = button)
        }
    }

    override fun onRender(canvas: Canvas) {
        val fps = engine.property.time.frequency()
        val pictureSize = engine.property.pictureSize
        //
        calculations.onPreRender()
        renders.onRender(canvas = canvas)
        //
        val fontHeight = 24.0
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = fontHeight,
            text = String.format("%6.2f", fps),
            pointTopLeft = pointOf(x = pictureSize.width - 128.0, y = pictureSize.height - fontHeight * 2),
        )
        listOf(
            String.format("m: %6.2f", env.camera.measure.magnitude),
        ).forEachIndexed { index, text ->
            canvas.texts.draw(
                color = Color.Green,
                fontHeight = fontHeight,
                text = text,
                pointTopLeft = pointOf(x = fontHeight * 2, y = pictureSize.height - fontHeight * (index + 2)),
            )
        }
    }

    override fun shouldEngineStop(): Boolean {
        return env.stopped
    }
}
