package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.features.animals.goats.data.model.GoatDao
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoatLocalDataSource @Inject constructor(
    private val goatDao: GoatDao
) {
    fun getAllGoatsStream(): Flow<List<Goat>> = goatDao.getAllGoats()

    fun getGoatStream(id: Int): Flow<Goat?> = goatDao.getGoat(id)

    suspend fun getGoatLocalIdByServerId(serverId: String): Int? = goatDao.getGoatLocalIdByServerId(serverId)

    suspend fun getEditedGoats(): List<Goat> = goatDao.getEditedGoats()

    suspend fun getDeletedGoats(): List<Goat> = goatDao.getDeletedGoats()
    suspend fun insertGoat(goat: Goat) = goatDao.insert(goat)

    suspend fun insertGoatList(goatList: List<Goat>) = goatList.forEach {
        insertGoat(it)
    }

    suspend fun updateGoat(goat: Goat) = goatDao.update(goat)

    suspend fun deleteGoat(goat: Goat) = goatDao.delete(goat)


}