package com.example.database

import com.example.kozaapp.data.network.AuthService
import com.example.kozaapp.data.network.RegistrationRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
){
    suspend fun login(username: String, password: String): Boolean {
        try{
            val response = authService.login(username, password)
            if (response.isSuccessful){
                response.body()?.access_token?.let {
                    tokenManager.saveAccessToken(it)
                }
                response.body()?.refresh_token?.let {
                    tokenManager.saveRefreshToken(it)
                }
                return response.isSuccessful
            }
            else{
                return false
                //ToDo: ошибка с логином
            }
        }
        catch(e: Exception){
            return false
            //ToDo: ошибка с логином
        }
    }



    suspend fun registration(request: RegistrationRequest): Boolean{
        try{
            val registrationResponse = authService.registration(request)
            if (registrationResponse.isSuccessful){
                val loginResponse = login(request.username, request.password)
                if (loginResponse == true){
                    return true
                }
                else{
                    return false
                    //ToDo: регистрация успешна, но ошибка с логином
                }
            }
            else{
                return false
                //ToDo: ошибка с регистрацией
            }
        }catch(e: Exception){
            return false
            //ToDo: ошибка с регистрацией
        }

    }
}
