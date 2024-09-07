package com.example.animalapp.data.di

import com.example.animalapp.data.repository.DefaultBreedRepository
import com.example.animalapp.domain.repository.BreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {


    @Binds
    internal abstract fun bindBreedRepository(
        defaultBreedRepository: DefaultBreedRepository
    ): BreedRepository
}