package com.example.animalapp.data.di

import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.datasource.local.DefaultBreedLocalDatasource
import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.datasource.remote.DefaultBreedRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DatasourceModule {


    @Binds
    internal abstract fun bindBreedRemoteDatasource(
        defaultBreedRemoteDatasource: DefaultBreedRemoteDatasource
    ): BreedRemoteDatasource

    @Binds
    internal abstract fun bindBreedLocalDatasource(
        defaultBreedLocalDatasource: DefaultBreedLocalDatasource
    ): BreedLocalDatasource
}