package com.crypto.currency.presentation.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CryptoPriceChart(
    chartData: List<Float>,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path()
            val width = size.width
            val height = size.height

            if (chartData.isNotEmpty()) {
                val minY = chartData.minOrNull() ?: 0f
                val maxY = chartData.maxOrNull() ?: 1f

                val xStep = width / (chartData.size - 1).coerceAtLeast(1)
                val yScale = height / (maxY - minY).coerceAtLeast(1f)

                // Move to the first point
                path.moveTo(0f, height - ((chartData[0] - minY) * yScale))

                // Draw the line
                chartData.forEachIndexed { index, value ->
                    val x = index * xStep
                    val y = height - ((value - minY) * yScale)
                    path.lineTo(x, y)
                }

                // Close the path for gradient fill (fill under line)
                path.lineTo(width, height)
                path.lineTo(0f, height)
                path.close()
            }

            // Draw gradient fill
            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x55FFA726), Color.Transparent) // Orange gradient
                ),
                style = Fill
            )

            // Draw the line
            drawPath(
                path = path,
                color = Color(0xFFFF9800), // Orange line
                style = Stroke(width = 5f, cap = StrokeCap.Round)
            )
        }
    }
}

