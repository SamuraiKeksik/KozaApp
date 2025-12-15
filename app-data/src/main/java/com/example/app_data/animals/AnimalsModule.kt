package com.example.app_data.animals

import android.content.Context
import com.example.app_data.animals.goats.DefaultGoatRepository
import com.example.app_data.animals.goats.GoatLocalDataSource
import com.example.app_data.animals.goats.GoatRemoteDataSource
import com.example.app_data.animals.goats.GoatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimalsModule {
    @Provides
    @Singleton
    fun provideGoatLocalDataSource(@ApplicationContext context: Context): GoatLocalDataSource {
        val goatDao = AppDatabase.getDatabase(context).goatDao()
        return GoatLocalDataSource(goatDao)
    }

    @Provides
    @Singleton
    fun provideGoatRemoteDataSource(
        //apiService: ApiService,
    ): GoatRemoteDataSource {
        //return GoatRemoteDataSource(apiService)
        return GoatRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideGoatRepository(
        goatLocalDataSource: GoatLocalDataSource,
        goatRemoteDataSource: GoatRemoteDataSource,
    ): GoatRepository {
        return DefaultGoatRepository(
            goatLocalDataSource,
            goatRemoteDataSource,
        )
    }
}