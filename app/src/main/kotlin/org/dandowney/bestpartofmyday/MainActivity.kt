package org.dandowney.bestpartofmyday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BestThemeOfMyDay {
        BestPartOfMyDayApp()
      }
    }
  }
}
