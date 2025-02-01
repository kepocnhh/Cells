package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.MutableOffset
import sp.kx.math.Size
import sp.kx.math.div
import sp.kx.math.measure.MutableDoubleMeasure
import sp.kx.math.measure.speedOf
import sp.kx.math.sizeOf
import test.lwjgl.cells.entity.IntSize
import test.lwjgl.cells.entity.MutableCamera
import test.lwjgl.cells.entity.MutableIntPoint

internal class CellsEngineLogics(
    private val engine: Engine,
) : EngineLogics {
    private val env = engine.let {
        val measure = MutableDoubleMeasure(24.0)
        val ps = it.property.pictureSize / measure
        val grid = IntSize(
            width = 8,
            height = 6,
        )
        val cellSize = sizeOf(2.0, 2.0)
        val selected = MutableIntPoint(x = 1, y = 2)
        val focused = MutableIntPoint(x = selected.x, y = selected.y)
        MutableEnvironment(
            grid = grid,
            cellSize = cellSize,
            selected = selected,
            focused = focused,
            camera = MutableCamera(
                measure = measure,
                speed = speedOf(8.0),
                offset = MutableOffset(
                    dX = (ps.width - cellSize.width) / 2 - focused.x * cellSize.width,
                    dY = (ps.height - cellSize.height) / 2 - focused.y * cellSize.height,
                ),
            ),
            offset = true,
            debug = true,
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
        calculations.onPreRender()
        renders.onRender(canvas = canvas)
    }

    override fun shouldEngineStop(): Boolean {
        return env.stopped
    }
}
