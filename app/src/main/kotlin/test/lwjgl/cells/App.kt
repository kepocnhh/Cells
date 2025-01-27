package test.lwjgl.cells

import sp.kx.lwjgl.engine.Engine
import sp.kx.math.sizeOf

fun main() {
    Engine.run(
        title = "Cells",
        supplier = ::CellsEngineLogics,
        size = sizeOf(640, 480),
        defaultFontName = "JetBrainsMono.ttf",
    )
}
