package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.features.animals.goats.data.model.GoatDao
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoatLocalDataSource @Inject constructor(
    private val goatDao: GoatDao
): GoatsDataSource {
    override fun getAllGoatsStream(): Flow<List<Goat>> = goatDao.getAllGoats()

    override fun getGoatStream(id: Int): Flow<Goat?> = goatDao.getGoat(id)

    override suspend fun insertGoat(goat: Goat) {
        val changedGoat = goat.copy(needsSync = true)
        goatDao.insert(changedGoat)
    }

    override suspend fun updateGoat(goat: Goat) {
        val changedGoat = goat.copy(needsSync = true)
        goatDao.update(changedGoat)
    }

    override suspend fun deleteGoat(goat: Goat){
        val changedGoat = goat.copy(isDeleted = true)
        goatDao.update(changedGoat)
    }

}