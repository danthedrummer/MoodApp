package org.dandowney.bestpartofmyday.library.colorpicker

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import android.graphics.Color as AndroidColor

data class HsvColor(
  val hue: Float,
  val saturation: Float,
  val value: Float,
) {

  fun toColor(): Color = Color.hsv(hue = hue, saturation = saturation, value = value)

  companion object {

    fun from(color: Color): HsvColor {
      val hsv = FloatArray(3)
      // Abusing the legacy Android color system to convert to HSV because I'm too lazy
      // to implement the maths myself and I don't want to use a library
      AndroidColor.RGBToHSV(
        (255 * color.red).toInt(),
        (255 * color.green).toInt(),
        (255 * color.blue).toInt(),
        hsv,
      )
      return HsvColor(
        hue = hsv[0],
        saturation = hsv[1],
        value = hsv[2],
      )
    }

    val saver: Saver<HsvColor, Any> = listSaver(
      save = { original ->
        listOf(
          original.hue,
          original.saturation,
          original.value,
        )
      },
      restore = { saved ->
        HsvColor(
          hue = saved[0],
          saturation = saved[1],
          value = saved[2],
        )
      },
    )
  }
}