package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.data.network.ApiService
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoatRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): GoatsDataSource{
    override fun getAllGoatsStream(): Flow<List<Goat>> {
        TODO("Not yet implemented")
    }

    override fun getGoatStream(id: Int): Flow<Goat?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertGoat(goat: Goat) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGoat(goat: Goat) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGoat(goat: Goat) {
        TODO("Not yet implemented")
    }

}