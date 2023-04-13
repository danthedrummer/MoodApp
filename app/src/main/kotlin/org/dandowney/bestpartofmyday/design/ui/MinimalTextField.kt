package org.dandowney.bestpartofmyday.design.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import org.dandowney.bestpartofmyday.design.theme.Gray500

@Composable
fun MinimalTextField(
  value: String,
  placeholder: String,
  textStyle: TextStyle,
  color: Color,
  placeholderColor: Color,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val interactionSource = remember { MutableInteractionSource() }

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    interactionSource = interactionSource,
    textStyle = textStyle.copy(
      color = color,
    ),
    cursorBrush = SolidColor(color),
    decorationBox = { innerTextField ->
      @OptIn(ExperimentalMaterialApi::class)
      TextFieldDefaults.TextFieldDecorationBox(
        value = value,
        innerTextField = innerTextField,
        enabled = true,
        singleLine = false,
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        placeholder = {
          Text(
            text = placeholder,
            style = textStyle.copy(
              color = placeholderColor,
            ),
          )
        }
      )
    }
  )

}