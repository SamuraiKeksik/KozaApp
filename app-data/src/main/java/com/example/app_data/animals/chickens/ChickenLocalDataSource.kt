package com.example.app_data.animals.chickens

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class ChickenLocalDataSource @Inject constructor(
    private val chickenDao: ChickenDao
) {
    fun getAllChickensStream(): Flow<List<ChickenEntity>> = chickenDao.getAllChickens()
    suspend fun getChickensModelsList(): List<ChickenModel> = chickenDao.getChickensModelsList()
    fun getChickenModelStream(id: UUID): Flow<ChickenModel?> = chickenDao.getChickenModel(id)
    suspend fun getChickenNameStream(id: UUID): String? = chickenDao.getChickenName(id)
    suspend fun getChickenGender(id: UUID): String = chickenDao.getChickenGender(id)
    suspend fun insertChicken(chickenEntity: ChickenEntity) = chickenDao.insert(chickenEntity)
    suspend fun insertChickenList(chickenEntityList: List<ChickenEntity>) = chickenEntityList.forEach {
        insertChicken(it)
    }
    suspend fun updateChicken(chickenEntity: ChickenEntity) = chickenDao.update(chickenEntity)
    suspend fun deleteChicken(chickenEntity: ChickenEntity) = chickenDao.delete(chickenEntity)

//    suspend fun getChickenLocalIdByServerId(serverId: String): Int? = chickenDao.getChickenLocalIdByServerId(serverId)
//    suspend fun getEditedChickens(): List<Chicken> = chickenDao.getEditedChickens()
//    suspend fun getDeletedChickens(): List<Chicken> = chickenDao.getDeletedChickens()

}