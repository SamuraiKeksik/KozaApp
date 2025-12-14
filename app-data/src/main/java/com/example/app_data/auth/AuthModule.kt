package com.example.app_data.auth

import android.content.Context
import com.example.app_data.animals.AppDatabase
import com.example.app_data.animals.DefaultGoatRepository
import com.example.app_data.animals.GoatLocalDataSource
import com.example.app_data.animals.GoatRemoteDataSource
import com.example.app_data.animals.GoatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
//        goatLocalDataSource: GoatLocalDataSource,
//        goatRemoteDataSource: GoatRemoteDataSource,
    ): AuthRepository {
        return AuthRepository(
//            goatLocalDataSource,
//            goatRemoteDataSource,
        )
    }
}