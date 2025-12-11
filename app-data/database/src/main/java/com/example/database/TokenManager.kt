package com.example.database

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context){
    private val prefs: SharedPreferences =
        context.getSharedPreferences("tokens_prefs", Context.MODE_PRIVATE)

    companion object{
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    fun saveAccessToken(token: String){
        prefs.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun saveRefreshToken(token: String){
        prefs.edit().putString(REFRESH_TOKEN_KEY, token).apply()
    }

    fun getAccessToken(): String?{
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String?{
        return prefs.getString(REFRESH_TOKEN_KEY, null)
    }

}