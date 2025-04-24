package kz.arctan.splines

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun SplineView() {
    val points = remember { mutableStateListOf<Offset>() }
    var selectedPoint by remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    points.add(offset)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        points.minByOrNull { (offset - it).getDistance() }?.let {
                            selectedPoint = it
                        }
                    },
                    onDrag = { change, dragAmount ->
                        selectedPoint?.let {
                            val newPoint = it + dragAmount
                            if (it in points)
                                points[points.indexOf(it)] = newPoint
                            selectedPoint = newPoint
                        }
                    },
                    onDragEnd = {
                        selectedPoint = null
                    }
                )
            }
    ) {
        drawRect(Color.LightGray)

        points.forEach { point ->
            drawCircle(Color.Blue, 8f, point)
        }

        if (points.size >= 2) {
            val path = Path().apply {
                moveTo(points.first().x, points.first().y)

                var i = 0
                while (i < points.size) {
                    if (i + 2 !in points.indices) break
                    else {
                        val p0 = points[i]
                        val p1 = points[i + 1]
                        val p2 = points[i + 2]

                        cubicTo(
                            p0.x, p0.y,
                            p1.x, p1.y,
                            p2.x, p2.y,
                        )
                        i++
                    }
                }

            }

            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(width = 4f)
            )
        }
    }
}
