package com.example.app_data.advertisements

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdvertisementsModule {

//    @Provides
//    @Singleton
//    fun provideAdvertisementLocalDataSource(@ApplicationContext context: Context): AdvertisementLocalDataSource {
//        val advertisementDao = AppDatabase.getDatabase(context).advertisementDao()
//        return AdvertisementLocalDataSource(advertisementDao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAdvertisementRemoteDataSource(
//        apiService: ApiService,
//    ): AdvertisementRemoteDataSource {
//        return AdvertisementRemoteDataSource(apiService)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAdvertisementsRepository(
//        advertisementRemoteDataSource: AdvertisementRemoteDataSource,
//        advertisementLocalDataSource: AdvertisementLocalDataSource,
//    ): AdvertisementRepository {
//        return AdvertisementRepository(
//            advertisementLocalDataSource,
//            advertisementRemoteDataSource,
//        )
//    }
}