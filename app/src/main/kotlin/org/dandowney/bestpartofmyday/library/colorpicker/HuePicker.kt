package org.dandowney.bestpartofmyday.library.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800

@Composable
fun HuePicker(
  modifier: Modifier = Modifier,
  currentColor: HsvColor,
  onHueChanged: (Float) -> Unit,
) {
  Canvas(
    modifier = modifier
      .fillMaxHeight()
      .pointerInput(Unit) {
        awaitEachGesture {
          val firstDown = awaitFirstDown()
          onHueChanged(getHue(firstDown.position.y, size.height.toFloat()))

          drag(firstDown.id) { change ->
            if (change.positionChange() != Offset.Zero) {
              change.consume()

              onHueChanged(
                getHue(change.position.y, size.height.toFloat()),
              )
            }
          }
        }
      },
  ) {
    val hueGradient = Brush.verticalGradient(
      colors = buildList {
        (0..360 step 60).forEach { hue ->
          add(Color.hsv(hue = hue.toFloat(), saturation = 1F, value = 1F))
        }
      },
    )
    drawRect(
      brush = hueGradient,
      size = size,
    )
    drawRect(
      color = Gray800,
      style = Stroke(Dp.Hairline.toPx()),
    )

    val stroke = 2.dp.toPx()
    val indicatorHeight = 8.dp.toPx()
    val indicatorOffset = Offset(
      x = -stroke,
      y = getVerticalOffset(currentColor.hue, size.height) - (indicatorHeight / 2),
    )
    val innerIndicatorOffset = indicatorOffset + Offset(stroke, stroke)

    drawRoundRect(
      topLeft = indicatorOffset,
      color = Gray800,
      style = Stroke(stroke),
      size = Size(width = size.width + stroke * 2, height = indicatorHeight),
      cornerRadius = CornerRadius(x = stroke * 2, y = stroke * 2),
    )
    drawRoundRect(
      topLeft = innerIndicatorOffset,
      color = Gray200,
      style = Stroke(stroke),
      size = Size(width = size.width, height = indicatorHeight - (stroke * 2)),
      cornerRadius = CornerRadius(x = stroke, y = stroke),
    )
  }
}

private fun getHue(y: Float, height: Float): Float = y.coerceIn(0F, height) * 360F / height

private fun getVerticalOffset(hue: Float, height: Float): Float = hue * height / 360F