package test.lwjgl.cells.entity

import java.util.Objects

internal class MutableIntPoint(
    override var x: Int,
    override var y: Int,
) : IntPoint {
    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is IntPoint -> x == other.x && y == other.y
            else -> false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    override fun toString(): String {
        return "{x: $x, y: $y}"
    }
}
