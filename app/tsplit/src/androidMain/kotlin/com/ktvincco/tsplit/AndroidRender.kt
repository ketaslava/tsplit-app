package com.ktvincco.tsplit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import androidx.core.graphics.createBitmap
import kotlin.math.cos
import kotlin.math.sin

fun surface2DToAndroidBitmap(surface2D: Surface2D, context: Context): Bitmap {
    // 1️⃣ Create bitmap
    val bitmap = createBitmap(surface2D.width, surface2D.height)
    val canvas = Canvas(bitmap)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 2️⃣ Draw each graphic object
    for (obj in surface2D.graphicObjects2D) {
        when (obj) {
            is Rectangle -> {
                paint.style = Paint.Style.FILL
                paint.color = obj.color
                canvas.drawRect(
                    obj.x,
                    obj.y,
                    (obj.x + obj.width),
                    (obj.y + obj.height),
                    paint
                )
            }

            is Text -> {
                paint.style = Paint.Style.FILL
                paint.color = obj.color
                paint.textSize = obj.size
                paint.typeface = Typeface.DEFAULT
                canvas.drawText(obj.text, obj.x, obj.y, paint)
            }

            is Circle -> {
                paint.style = Paint.Style.FILL
                paint.color = obj.color
                canvas.drawCircle(obj.cx, obj.cy, obj.radius, paint)
            }

            is Line -> {
                paint.style = Paint.Style.STROKE
                paint.color = obj.color
                paint.strokeWidth = obj.thickness
                canvas.drawLine(obj.x1, obj.y1, obj.x2, obj.y2, paint)
            }

            is Pixel -> {
                paint.style = Paint.Style.FILL
                paint.color = obj.color
                canvas.drawPoint(obj.x, obj.y, paint)
            }

            is RectangleGradient -> {
                paint.style = Paint.Style.FILL
                val intColors = obj.colors // already IntArray

                // Calculate start and end points based on angle
                val angleRad = Math.toRadians((obj.angle - 45).toDouble())
                val cx = obj.x + obj.width / 2f
                val cy = obj.y + obj.height / 2f
                val halfDiagX = obj.width / 2f * cos(angleRad) - obj.height / 2f * sin(angleRad)
                val halfDiagY = obj.width / 2f * sin(angleRad) + obj.height / 2f * cos(angleRad)

                val x0 = cx - halfDiagX.toFloat()
                val y0 = cy - halfDiagY.toFloat()
                val x1 = cx + halfDiagX.toFloat()
                val y1 = cy + halfDiagY.toFloat()

                val shader = LinearGradient(
                    x0, y0, x1, y1,
                    intColors,
                    obj.positions,
                    Shader.TileMode.CLAMP
                )

                paint.shader = shader
                canvas.drawRect(
                    obj.x,
                    obj.y,
                    (obj.x + obj.width),
                    (obj.y + obj.height),
                    paint
                )
                paint.shader = null
            }

            is RadialGradient -> {
                paint.style = Paint.Style.FILL
                val intColors = obj.colors

                val cx = obj.x + obj.width / 2f
                val cy = obj.y + obj.height / 2f
                val radius = obj.width.coerceAtLeast(obj.height) / 2f

                val shader = android.graphics.RadialGradient(
                    cx, cy, radius,
                    intColors,
                    obj.positions,
                    Shader.TileMode.CLAMP
                )

                paint.shader = shader
                canvas.drawRect(
                    obj.x,
                    obj.y,
                    (obj.x + obj.width),
                    (obj.y + obj.height),
                    paint
                )
                paint.shader = null
            }

            is Image2D -> {
                // Load the image from assets or resources using the link
                val inputStream = context.assets.open(obj.link)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                // Draw the bitmap on the canvas scaled to the rectangle
                val dstRect = Rect(obj.x, obj.y, obj.x
                        + obj.width, obj.y + obj.height)
                canvas.drawBitmap(bitmap, null, dstRect, paint)
            }
        }
    }

    return bitmap
}