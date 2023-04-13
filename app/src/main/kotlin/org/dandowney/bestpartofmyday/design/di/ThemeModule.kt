package org.dandowney.bestpartofmyday.design.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dandowney.bestpartofmyday.design.theme.BestThemeRepository
import org.dandowney.bestpartofmyday.design.theme.ThemeRepository

@Module
@InstallIn(SingletonComponent::class)
interface ThemeModule {

  @Binds
  fun themeRepository(bestThemeRepository: BestThemeRepository): ThemeRepository
}