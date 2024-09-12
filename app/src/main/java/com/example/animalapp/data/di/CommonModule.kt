package com.example.animalapp.data.di

import com.example.animalapp.data.common.stringProvider.DefaultStringProvider
import com.example.animalapp.data.common.stringProvider.StringProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CommonModule {


    @Binds
    internal abstract fun bindStringProvider(
        defaultStringProvider: DefaultStringProvider
    ): StringProvider
}