package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.features.animals.goats.data.model.GoatDao
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow

class OfflineGoatsRepository(private val goatDao: GoatDao): GoatsRepository {
    override fun getAllGoatsStream(): Flow<List<Goat>> = goatDao.getAllGoats()

    override fun getGoatStream(id: Int): Flow<Goat?> = goatDao.getGoat(id)

    override suspend fun insertGoat(goat: Goat) = goatDao.insert(goat)

    override suspend fun updateGoat(goat: Goat) = goatDao.update(goat)

    override suspend fun deleteGoat(goat: Goat) = goatDao.delete(goat)

}