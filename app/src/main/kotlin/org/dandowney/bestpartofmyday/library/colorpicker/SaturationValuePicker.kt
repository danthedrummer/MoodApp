package org.dandowney.bestpartofmyday.library.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800


@Composable
fun SaturationValuePicker(
  hue: Float,
  currentColor: HsvColor,
  modifier: Modifier = Modifier,
  onSaturationValueChanged: (Float, Float) -> Unit,
) {
  Canvas(
    modifier = modifier
      .fillMaxHeight()
      .pointerInput(Unit) {
        awaitEachGesture {
          val firstDown = awaitFirstDown()
          onSaturationValueChanged(
            getSaturation(firstDown.position.x, size.width.toFloat()),
            getValue(firstDown.position.y, size.height.toFloat()),
          )

          drag(firstDown.id) { change ->
            if (change.positionChange() != Offset.Zero) {
              change.consume()

              onSaturationValueChanged(
                getSaturation(change.position.x, size.width.toFloat()),
                getValue(change.position.y, size.height.toFloat()),
              )
            }
          }
        }
      },
  ) {
    val valueGradient = Brush.verticalGradient(
      colors = listOf(
        Color.White,
        Color.Black,
      ),
    )
    val saturationGradient = Brush.horizontalGradient(
      colors = listOf(
        Color.hsv(hue = hue, saturation = 0F, value = 1F),
        Color.hsv(hue = hue, saturation = 1F, value = 1F),
      ),
    )

    drawRect(
      brush = valueGradient,
      size = size,
    )
    drawRect(
      brush = saturationGradient,
      size = size,
      blendMode = BlendMode.Multiply,
    )

    val strokeWidth = 2.dp.toPx()
    val outerIndicatorRadius = 6.dp.toPx()
    val indicatorCenter = Offset(
      x = getHorizontalOffset(currentColor.saturation, size.width),
      y = getVerticalOffset(currentColor.value, size.height),
    )
    drawCircle(
      color = Gray800,
      radius = outerIndicatorRadius,
      center = indicatorCenter,
      style = Stroke(strokeWidth),
    )
    drawCircle(
      color = Gray200,
      radius = outerIndicatorRadius - strokeWidth,
      style = Stroke(strokeWidth),
      center = indicatorCenter
    )
  }
}

private fun getSaturation(x: Float, width: Float): Float = x.coerceIn(0F, width) * 1F / width

private fun getHorizontalOffset(saturation: Float, width: Float): Float {
  return saturation * width
}

private fun getValue(y: Float, height: Float): Float = 1F - y.coerceIn(0F, height) * 1F / height

private fun getVerticalOffset(value: Float, height: Float): Float {
  return (1F - value) * height
}