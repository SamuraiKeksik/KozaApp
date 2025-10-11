package com.example.kozaapp.data

import android.util.Log
import com.example.kozaapp.data.network.ApiService
import com.example.kozaapp.features.auth.data.model.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCurrentUser(): User?{
        try{
            val response = apiService.getCurrentUser()
            return response.body()
            //ToDo: вывод исключения при ошибке
        }
        catch (e: Exception){
            //ToDo: вывод исключения при ошибке
            Log.e("NetworkError", e.message.toString())
            Log.e("NetworkError", e.message.toString())
        }
        return null
    }
}