package com.ktvincco.tsplit

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

class Surface2D (var width: Int, var height: Int) {

    var graphicObjects2D: List<GraphicObject2D> = listOf()

    fun drawObject(graphicObject: GraphicObject2D) {
        graphicObjects2D += graphicObject
    }
}

open class GraphicObject2D

class Rectangle (var x: Float, var y: Float, var width: Float,
                var height: Float, var color: Int): GraphicObject2D() {
    constructor(x: Float, y: Float, width: Float, height: Float, color: Color) :
            this(x, y, width, height, color.toArgb())
}


class Circle(var cx: Float, var cy: Float, var radius: Float, var color: Int) : GraphicObject2D() {
    constructor(cx: Float, cy: Float, radius: Float, color: Color) :
            this(cx, cy, radius, color.toArgb())
}


class Line(var x1: Float, var y1: Float, var x2: Float, var y2: Float, var color: Int,
           var thickness: Float = 1f) : GraphicObject2D() {
    constructor(x1: Float, y1: Float, x2: Float, y2: Float, color: Color, thickness: Float = 1f) :
            this(x1, y1, x2, y2, color.toArgb(), thickness)
}

class Pixel(var x: Float, var y: Float, var color: Int) : GraphicObject2D() {
    constructor(x: Float, y: Float, color: Color) : this(x, y, color.toArgb())
}

class Text (var text: String, var x: Float, var y: Float,
            var size: Float, var color: Int): GraphicObject2D() {
    constructor(text: String, x: Float, y: Float, size: Float, color: Color) :
            this(text, x, y, size, color.toArgb())
}

class RectangleGradient(var x: Float, var y: Float, var width: Float, var height: Float,
                        var colors: IntArray, var angle: Float = 0f,
                        var positions: FloatArray? = null) : GraphicObject2D() {
    constructor(x: Float, y: Float, width: Float, height: Float, colors: List<Color>,
                angle: Float = 0f, positions: FloatArray? = null) : this(
        x, y, width, height, colors.map { it.toArgb() }.toIntArray(), angle, positions)
}

class RadialGradient(var x: Float, var y: Float, var width: Float, var height: Float,
                     var colors: IntArray, var positions: FloatArray? = null) : GraphicObject2D() {
    constructor(x: Float, y: Float, width: Float, height: Float, colors: List<Color>,
                positions: FloatArray? = null) : this(
        x, y, width, height, colors.map { it.toArgb() }.toIntArray(), positions)
}


class Image2D(
    var link: String,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int
) : GraphicObject2D()


fun Color.toArgb(): Int {
    val a = (alpha * 255f).roundToInt().coerceIn(0, 255)
    val r = (red   * 255f).roundToInt().coerceIn(0, 255)
    val g = (green * 255f).roundToInt().coerceIn(0, 255)
    val b = (blue  * 255f).roundToInt().coerceIn(0, 255)
    return (a shl 24) or (r shl 16) or (g shl 8) or b
}