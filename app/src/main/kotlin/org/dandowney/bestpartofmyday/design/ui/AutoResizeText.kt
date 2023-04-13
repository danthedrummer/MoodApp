package org.dandowney.bestpartofmyday.design.ui

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeText(
  text: String,
  fontSizeRange: FontSizeRange,
  modifier: Modifier = Modifier,
  color: Color = Color.Unspecified,
  fontStyle: FontStyle? = null,
  fontWeight: FontWeight? = null,
  fontFamily: FontFamily? = null,
  letterSpacing: TextUnit = TextUnit.Unspecified,
  textDecoration: TextDecoration? = null,
  textAlign: TextAlign? = null,
  lineHeight: TextUnit = TextUnit.Unspecified,
  overflow: TextOverflow = TextOverflow.Clip,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  minLines: Int = 1,
  style: TextStyle = LocalTextStyle.current,
) {
  var fontSize by remember {
    mutableStateOf(fontSizeRange.max.value)
  }
  var readyToDraw by remember {
    mutableStateOf(false)
  }
  Text(
    text = text,
    color = color,
    fontSize = fontSize.sp,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    minLines = minLines,
    style = style,
    onTextLayout = { textLayoutResult ->
      if (textLayoutResult.didOverflowHeight && !readyToDraw) {
        val newSize = fontSize - fontSizeRange.step.value
        if (newSize < fontSizeRange.min.value) {
          fontSize = fontSizeRange.min.value
          readyToDraw = true
        } else {
          fontSize = newSize
        }
      } else {
        readyToDraw = true
      }
    },
    modifier = modifier
      .drawWithContent {
        if (readyToDraw) drawContent()
      },
  )
}

data class FontSizeRange(
  val min: TextUnit,
  val max: TextUnit,
  val step: TextUnit = DEFAULT_RESIZE_STEP_SIZE,
) {

  init {
    require(min < max) { "FontSizeRange min must be lower than max (min = $min, max = $max)" }
    require(step.value > 0) { "FontSizeRange step must be greater than 0 (step = $step)" }
  }

  companion object {
    private val DEFAULT_RESIZE_STEP_SIZE = 1.sp
  }
}
