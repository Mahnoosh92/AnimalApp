package com.example.animalapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.animalapp.data.db.AnimalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    @Named("app_db")
    fun provideCatDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, AnimalDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBreedDao(@Named("app_db") db: AnimalDatabase) = db.BreedDao()

}