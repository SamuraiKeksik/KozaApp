package com.example.kozaapp.data

import android.content.Context
import com.example.kozaapp.data.network.ApiService
import com.example.kozaapp.data.network.AuthService
import com.example.kozaapp.features.animals.goats.data.GoatsDataSource
import com.example.kozaapp.features.animals.goats.data.GoatLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val BASE_URL = "http://192.168.1.231:8000"

    @Provides
    @Singleton
    fun provideApiService(
        tokenManager: TokenManager,
    ): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val originRequest = chain.request()
                    val newRequest = originRequest.newBuilder()
                        .header("Authorization", "Bearer ${tokenManager.getAccessToken()}")
                        .build()
                    chain.proceed(newRequest)
                }
            ).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        tokenManager: TokenManager,
    ): AuthRepository {
        return AuthRepository(authService, tokenManager)
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        apiService: ApiService,
    ): MainRepository {
        return MainRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideGoatsRepository(
        @ApplicationContext context: Context
    ): GoatsDataSource {
        return GoatLocalDataSource(
            AnimalsDatabase.getDatabase(context).goatDao()
        )
    }
}
