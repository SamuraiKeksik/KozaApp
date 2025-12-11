package com.example.app_data.animals

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GoatLocalDataSource @Inject constructor(
    private val goatDao: GoatDao
) {
    fun getAllGoatsStream(): Flow<List<Goat>> = goatDao.getAllGoats()
    fun getGoatStream(id: UUID): Flow<Goat?> = goatDao.getGoat(id)
    suspend fun insertGoat(goat: Goat) = goatDao.insert(goat)
    suspend fun insertGoatList(goatList: List<Goat>) = goatList.forEach {
        insertGoat(it)
    }
    suspend fun updateGoat(goat: Goat) = goatDao.update(goat)
    suspend fun deleteGoat(goat: Goat) = goatDao.delete(goat)

//    suspend fun getGoatLocalIdByServerId(serverId: String): Int? = goatDao.getGoatLocalIdByServerId(serverId)
//    suspend fun getEditedGoats(): List<Goat> = goatDao.getEditedGoats()
//    suspend fun getDeletedGoats(): List<Goat> = goatDao.getDeletedGoats()

}