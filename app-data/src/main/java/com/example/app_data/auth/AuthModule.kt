package com.example.app_data.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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