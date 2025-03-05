package com.crypto.currency.presentation.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CryptoPriceChart(
    priceData: List<Float>,
    modifier: Modifier = Modifier
) {
    if (priceData.isEmpty()) return

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
    ) {
        val spacing = size.width / (priceData.size - 1)
        val maxPrice = priceData.maxOrNull() ?: 0f
        val minPrice = priceData.minOrNull() ?: 0f
        val priceRange = maxPrice - minPrice

        val path = Path().apply {
            val firstX = 0f
            val firstY = size.height - ((priceData[0] - minPrice) / priceRange) * size.height
            moveTo(firstX, firstY)

            priceData.forEachIndexed { index, price ->
                val x = index * spacing
                val y = size.height - ((price - minPrice) / priceRange) * size.height
                lineTo(x, y)
            }
        }

        // Draw the main price line
        drawPath(
            path = path,
            color = Color(0xFFFFA500), // Orange color for graph
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Draw a dotted path effect (if needed)
        drawPath(
            path = path,
            color = Color(0xFFFFA500),
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )
    }
}
