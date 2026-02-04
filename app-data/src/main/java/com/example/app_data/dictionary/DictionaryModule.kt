package com.example.app_data.dictionary

import android.content.Context
import com.example.app_data.database.AppDatabase
import com.example.app_data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {
    @Provides
    @Singleton
    fun provideDictionaryLocalDataSource(@ApplicationContext context: Context): DictionaryLocalDataSource {
        val dictionaryDao = AppDatabase.getDatabase(context).dictionaryDao()
        return DictionaryLocalDataSource(dictionaryDao)
    }

    @Provides
    @Singleton
    fun provideDictionaryRemoteDataSource(
        apiService: ApiService,
    ): DictionaryRemoteDataSource {
        return DictionaryRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        dictionaryLocalDataSource: DictionaryLocalDataSource,
        dictionaryRemoteDataSource: DictionaryRemoteDataSource,
    ): DictionaryRepository {
        return DefaultDictionaryRepository(dictionaryLocalDataSource, dictionaryRemoteDataSource)
    }





}