package com.example.app_data.animals

import android.content.Context
import com.example.app_data.animals.chickens.ChickenLocalDataSource
import com.example.app_data.animals.chickens.ChickenRepository
import com.example.app_data.animals.chickens.DefaultChickenRepository
import com.example.app_data.animals.cows.CowLocalDataSource
import com.example.app_data.animals.cows.CowRepository
import com.example.app_data.animals.cows.DefaultCowRepository
import com.example.app_data.animals.goats.DefaultGoatRepository
import com.example.app_data.animals.goats.GoatLocalDataSource
import com.example.app_data.animals.goats.GoatRemoteDataSource
import com.example.app_data.animals.goats.GoatRepository
import com.example.app_data.database.AppDatabase
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
    fun provideCowLocalDataSource(@ApplicationContext context: Context): CowLocalDataSource {
        val cowDao = AppDatabase.getDatabase(context).cowDao()
        return CowLocalDataSource(cowDao)
    }
    @Provides
    @Singleton
    fun provideChickenLocalDataSource(@ApplicationContext context: Context): ChickenLocalDataSource {
        val chickenDao = AppDatabase.getDatabase(context).chickenDao()
        return ChickenLocalDataSource(chickenDao)
    }
//    @Provides
//    @Singleton
//    fun provideGoatRemoteDataSource(
//        //apiService: ApiService,
//    ): GoatRemoteDataSource {
//        //return GoatRemoteDataSource(apiService)
//        return GoatRemoteDataSource()
//    }

    @Provides
    @Singleton
    fun provideGoatRepository(
        goatLocalDataSource: GoatLocalDataSource,
        //goatRemoteDataSource: GoatRemoteDataSource,
        animalsRepository: AnimalsRepository
    ): GoatRepository {
        return DefaultGoatRepository(
            animalsRepository,
            goatLocalDataSource,
            //goatRemoteDataSource,
        )
    }
    @Provides
    @Singleton
    fun provideCowsRepository(
        cowLocalDataSource: CowLocalDataSource,
        animalsRepository: AnimalsRepository
    ): CowRepository {
        return DefaultCowRepository(
            animalsRepository,
            cowLocalDataSource,
        )
    }
    @Provides
    @Singleton
    fun provideChickensRepository(
        chickenLocalDataSource: ChickenLocalDataSource,
        animalsRepository: AnimalsRepository
    ): ChickenRepository {
        return DefaultChickenRepository(
            animalsRepository,
            chickenLocalDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideAnimalsLocalDataSources(@ApplicationContext context: Context): AnimalsLocalDataSource {
        val animalsDao = AppDatabase.getDatabase(context).animalsDao()
        return AnimalsLocalDataSource(animalsDao)
    }
    @Provides
    @Singleton
    fun provideAnimalsRepository(animalsLocalDataSource: AnimalsLocalDataSource): AnimalsRepository =
        DefaultAnimalsRepository(animalsLocalDataSource)
}