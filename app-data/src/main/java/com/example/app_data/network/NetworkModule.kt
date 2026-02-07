package com.example.app_data.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://144.31.70.140:8000"
    //private const val BASE_URL = "http://192.168.1.231:8000"
    //private const val BASE_URL = "http://192.168.1.179:8000"

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideApiService(
//        tokenManager: TokenManager,
//    ): ApiService {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(
//                Interceptor { chain ->
//                    val originRequest = chain.request()
//                    val newRequest = originRequest.newBuilder()
//                        .header("Authorization", "Bearer ${tokenManager.getAccessToken()}")
//                        .build()
//                    chain.proceed(newRequest)
//                }
//            ).build()
//
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .build()
//            .create(ApiService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthService(): AuthService {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(AuthService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
//        return TokenManager(context)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthRepository(
//        authService: AuthService,
//        tokenManager: TokenManager,
//    ): AuthRepository {
//        return AuthRepository(authService, tokenManager)
//    }
}