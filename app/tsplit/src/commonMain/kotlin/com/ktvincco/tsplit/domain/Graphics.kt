package com.ktvincco.tsplit.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.ktvincco.tsplit.Circle
import com.ktvincco.tsplit.Line
import com.ktvincco.tsplit.Rectangle
import com.ktvincco.tsplit.RectangleGradient
import com.ktvincco.tsplit.Surface2D
import com.ktvincco.tsplit.Text

class Graphics {


    fun drawGrid(surface: Surface2D, startX: Float, startY: Float, endX: Float, endY: Float,
                 rows: Int, columns: Int, thickness: Float): Surface2D {
        // Draw grid
        var position = 0F
        while (position in startX..endX) {
            surface.drawObject(Line(position, 0F,
                position, surface.height.toFloat(), Color.Gray, thickness))
            position += (endX - startX) / columns
        }
        position = 0F
        while (position in startY..endY) {
            surface.drawObject(Line(0F, position,
                surface.width.toFloat(), position, Color.Gray, thickness))
            position += (endY - startY) / rows
        }
        return surface
    }


    fun drawButtons(surface: Surface2D, stack: Stack, layer: String, startX: Float,
                    startY: Float, endX: Float, endY: Float, rows: Int, columns: Int,
                    overlayLayer: String? = null): Surface2D {

        val buttonSize = Pair((endX - startX) / columns.toFloat(),
            (endY - startY) / rows.toFloat())

        for (y in 0..rows - 1) {
            for (x in 0..columns - 1) {
                val position = (columns * y) + x

                // Get button for position
                var button = stack.getLayerByName(layer)?.getButtonByPosition(position)
                val overlayButton = stack.getLayerByName(overlayLayer?: "")?.getButtonByPosition(position)
                if (overlayButton != null) button = overlayButton
                val color = if (overlayButton == null) Color.Red else Color.Yellow

                // Check
                if (button == null || button.previewText == null) continue

                // Draw text
                surface.drawObject(Text(
                    button.previewText, startX + (x * buttonSize.first) + 40F,
                    startY + ((y + 1) * buttonSize.second) - 40F, 72F, color))

                // Draw indicators
                // Stack or gesture
                if (button.stackIndicator || button.gestureIndicator) {
                    surface.drawObject(Circle(
                        startX + ((x + 1) * buttonSize.first) - 50F,
                        startY + (y * buttonSize.second) + 50F,
                        10F,
                        Color.Red)
                    )
                }
                // Shift
                if (button.shiftIndicator) {
                    val x = startX + (x * buttonSize.first) + 40F
                    val y = startY + (y * buttonSize.second) + 40F
                    surface.drawObject(Rectangle(
                        x, y, 20F, 20F, Color.Red)
                    )
                }
            }
        }

        return surface
    }


    fun drawTouch(surface: Surface2D, startX: Float, startY: Float, endX: Float, endY: Float,
                  gridX: Int, gridY: Int, rows: Int, columns: Int): Surface2D {

        // Draw touch
        val size = Pair((endX - startX) / columns, (endY - startY) / rows)
        surface.drawObject(RectangleGradient(
            (gridX * size.first) + startX,
            (gridY * size.second) + startY,
            size.first, size.second, angle = 45F,
            colors = listOf(Color.Red, Color.Blue)))

        return surface
    }


}