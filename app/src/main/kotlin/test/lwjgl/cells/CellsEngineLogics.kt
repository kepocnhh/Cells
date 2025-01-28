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
            )
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
        //
        calculations.onPreRender()
        renders.onRender(canvas = canvas)
        //
        val ps = engine.property.pictureSize
//        val c = ps.div(env.camera.measure).centerPoint()
//        val p = Point.Center.plus(env.camera.offset)
//        val r = c.moved(
//            length = distanceOf(c, p),
//            angle = angleOf(c, p) + speedOf(1.0).length(engine.property.time.diff()),
//        )
//        env.camera.offset.set(r.x,r.y)
        val fontHeight = 24.0
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = fontHeight,
            text = String.format("%6.2f", fps),
            pointTopLeft = pointOf(x = ps.width - 128.0, y = ps.height - fontHeight * 2),
        )
        val center = ps.div(env.camera.measure).center().minus(env.camera.offset)
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = fontHeight,
            text = String.format("%+07.2f:%+07.2f", center.dX, center.dY),
            pointTopLeft = pointOf(x = fontHeight * 2, y = ps.height - fontHeight * 3),
        )
        canvas.texts.draw(
            color = Color.Green,
            fontHeight = fontHeight,
            text = String.format("%6.2f", env.camera.measure.magnitude),
            pointTopLeft = pointOf(x = fontHeight * 2, y = ps.height - fontHeight * 2),
        )
        //
        canvas.polygons.drawCircle(
            color = Color.Yellow,
            pointCenter = Point.Center.plus(env.camera.offset),
            radius = 0.25,
            edgeCount = 4,
            measure = env.camera.measure,
        )
    }

    override fun shouldEngineStop(): Boolean {
        return env.stopped
    }
}
