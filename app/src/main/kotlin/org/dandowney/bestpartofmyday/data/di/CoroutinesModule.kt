package org.dandowney.bestpartofmyday.data.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesModule {

  @Provides
  @Reusable
  fun scope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
}
