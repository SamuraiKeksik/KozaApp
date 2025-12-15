package com.example.app_data.animals.goats

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GoatLocalDataSource @Inject constructor(
    private val goatDao: GoatDao
) {
    fun getAllGoatsStream(): Flow<List<GoatEntity>> = goatDao.getAllGoats()
    fun getGoatStream(id: UUID): Flow<GoatEntity?> = goatDao.getGoat(id)
    suspend fun insertGoat(goatEntity: GoatEntity) = goatDao.insert(goatEntity)
    suspend fun insertGoatList(goatEntityList: List<GoatEntity>) = goatEntityList.forEach {
        insertGoat(it)
    }
    suspend fun updateGoat(goatEntity: GoatEntity) = goatDao.update(goatEntity)
    suspend fun deleteGoat(goatEntity: GoatEntity) = goatDao.delete(goatEntity)

//    suspend fun getGoatLocalIdByServerId(serverId: String): Int? = goatDao.getGoatLocalIdByServerId(serverId)
//    suspend fun getEditedGoats(): List<Goat> = goatDao.getEditedGoats()
//    suspend fun getDeletedGoats(): List<Goat> = goatDao.getDeletedGoats()

}