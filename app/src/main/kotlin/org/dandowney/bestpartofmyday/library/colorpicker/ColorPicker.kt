package org.dandowney.bestpartofmyday.library.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ColorPicker(
  modifier: Modifier = Modifier,
  hsvColor: HsvColor = HsvColor.from(Color.Red),
  onColorChanged: (color: HsvColor) -> Unit,
) {

  var internalState by remember {
    mutableStateOf(hsvColor)
  }
  val internalValue = internalState.copy(
    hue = hsvColor.hue,
    saturation = hsvColor.saturation,
    value = hsvColor.value,
  )

  SideEffect {
    internalState = internalValue
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(300.dp)
      .background(color = MaterialTheme.colors.background)
      .padding(16.dp),
    horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp),
  ) {
    SaturationValuePicker(
      hue = hsvColor.hue,
      modifier = Modifier.weight(3F),
      currentColor = internalValue,
      onSaturationValueChanged = { saturation, value ->
        internalState = internalState.copy(
          saturation = saturation,
          value = value,
        )
        onColorChanged(internalState)
      },
    )

    Column(
      modifier = Modifier
        .width(64.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      HuePicker(
        modifier = Modifier
          .width(32.dp),
        currentColor = internalValue,
        onHueChanged = { hue ->
          println("DanDebug: setting new hue -> $hue")
          internalState = internalState.copy(
            hue = hue,
          )
          println("DanDebug: updated state -> $internalState")
          onColorChanged(internalState)
        },
      )
    }
  }
}
