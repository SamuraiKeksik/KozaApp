package com.example.app_data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

//    @Provides
//    @Singleton
//    fun provideMainRepository(
//        apiService: ApiService,
//    ): MainRepository {
//        return MainRepository(apiService)
//    }




}