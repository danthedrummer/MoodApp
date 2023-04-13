package org.dandowney.bestpartofmyday.design.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BestThemeRepository @Inject constructor(
  @ApplicationContext private val context: Context,
) : ThemeRepository {

  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

  override suspend fun isDarkTheme(): Boolean {
    val key = booleanPreferencesKey(name = KEY_DARK_THEME)
    return context.dataStore.data.first()[key] ?: false
  }

  override suspend fun setDarkTheme(isDarkTheme: Boolean) {
    val key = booleanPreferencesKey(name = KEY_DARK_THEME)
    context.dataStore.edit { preferences ->
      preferences[key] = isDarkTheme
    }
  }

  companion object {
    private const val DATA_STORE_NAME = "best_theme_data_store"
    private const val KEY_DARK_THEME = "dark_theme"
  }
}
