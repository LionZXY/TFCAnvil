package uk.kulikov.anvil.composable.tool

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalPointerSlopOrCancellation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFirstOrNull
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun TargetSliderComposable(
    counter: Int,
    onCounterChange: (Int) -> Unit,
) {
    TargetRuler(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        counter = counter,
        onCounterChange = onCounterChange
    )
}

private const val MIN_VALUE = 0
private const val MAX_VALUE = 140
private const val RANGE = MAX_VALUE - MIN_VALUE


@Composable
private fun TargetRuler(
    counter: Int,
    onCounterChange: (Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 12.sp
    )
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                val startX = innerPadding.calculateStartPadding(LayoutDirection.Ltr).toPx()
                val endXOffset = innerPadding.calculateEndPadding(LayoutDirection.Ltr).toPx()
                val pxToPoint = (size.width - startX - endXOffset) / RANGE
                followClickHorizontal { offsetX ->
                    val value = ((offsetX - startX) / pxToPoint).roundToInt()
                    onCounterChange(value)
                }
            }
    ) {
        val startX = innerPadding.calculateStartPadding(LayoutDirection.Ltr).toPx()
        val endX = size.width - innerPadding.calculateEndPadding(LayoutDirection.Ltr).toPx()
        val height = size.height
        val baseY = height - 20.dp.toPx()

        // Draw baseline
        drawLine(
            color = Color.Gray,
            start = Offset(startX, baseY),
            end = Offset(endX, baseY),
            strokeWidth = 2f
        )

        // Draw ticks
        for (i in MIN_VALUE..MAX_VALUE) {
            val x = startX + ((i - MIN_VALUE).toFloat() / RANGE) * (endX - startX)
            val tickHeight = when {
                i % 20 == 0 -> 24.dp.toPx()
                i % 10 == 0 -> 18.dp.toPx()
                i % 5 == 0 -> 14.dp.toPx()
                else -> 10.dp.toPx()
            }

            val color = when {
                i % 20 == 0 -> Color.Black
                i % 10 == 0 -> Color(0xFF49713F)
                else -> Color.Gray
            }

            drawLine(
                color = color,
                start = Offset(x, baseY),
                end = Offset(x, baseY - tickHeight),
                strokeWidth = if (i % 10 == 0) 6f else 3f
            )

            if (i % 20 == 0) {
                val width = 40.dp.toPx()
                drawText(
                    textMeasurer = textMeasurer,
                    text = i.toString(),
                    style = textStyle,
                    topLeft = Offset(
                        x = x - width / 2,
                        y = baseY - 4.dp.toPx()
                    ),
                    size = Size(
                        width = width,
                        height = 20.dp.toPx()
                    )
                )
            }
        }
        val lineOffsetX = 6.dp.toPx()
        val pxToPoint = (endX - startX) / RANGE
        val offset = counter * pxToPoint + startX
        val highlightY = baseY + 4.dp.toPx()
        val heightLine = ((highlightY - lineOffsetX) / 2) - 2.dp.toPx()
        drawLine(
            color = Color(0xFFDE401F),
            start = Offset(offset, highlightY),
            end = Offset(offset, highlightY - heightLine),
            strokeWidth = 6f
        )
        drawLine(
            color = Color(0xFFDE401F),
            start = Offset(offset, lineOffsetX),
            end = Offset(offset, heightLine + lineOffsetX),
            strokeWidth = 6f
        )
    }
}

private suspend fun PointerInputScope.followClickHorizontal(
    onClick: (Float) -> Unit
) {
    awaitEachGesture {
        val firstEvent = awaitFirstDown()
        firstEvent.consume()
        onClick(firstEvent.position.x)
        var currentOffset = firstEvent.position.x
        val drag = awaitHorizontalPointerSlopOrCancellation(
            firstEvent.id,
            firstEvent.type
        ) { change, over ->
            change.consume()
            currentOffset = firstEvent.position.x + over
        }
        if (drag != null) {
            onClick(currentOffset)

            horizontalDrag(drag.id) {
                onClick(it.position.x)
                it.consume()
            }
        }
    }
}