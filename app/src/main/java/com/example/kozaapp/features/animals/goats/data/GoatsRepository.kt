package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow

interface GoatsRepository {
    fun getAllGoatsStream(): Flow<List<Goat>>
    fun getGoatStream(id: Int): Flow<Goat?>
    suspend fun insertGoat(goat: Goat)
    suspend fun updateGoat(goat: Goat)
    suspend fun deleteGoat(goat: Goat)
}