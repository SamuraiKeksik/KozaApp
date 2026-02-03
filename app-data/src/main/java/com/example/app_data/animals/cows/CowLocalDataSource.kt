package com.example.app_data.animals.cows

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CowLocalDataSource @Inject constructor(
    private val cowDao: CowDao
) {
    fun getAllCowsStream(): Flow<List<CowEntity>> = cowDao.getAllCows()
    suspend fun getCowsModelsList(): List<CowModel> = cowDao.getCowsModelsList()
    fun getCowModelStream(id: UUID): Flow<CowModel?> = cowDao.getCowModel(id)
    suspend fun getCowNameStream(id: UUID): String? = cowDao.getCowName(id)
    suspend fun getCowGender(id: UUID): String = cowDao.getCowGender(id)
    suspend fun insertCow(cowEntity: CowEntity) = cowDao.insert(cowEntity)
    suspend fun insertCowList(cowEntityList: List<CowEntity>) = cowEntityList.forEach {
        insertCow(it)
    }
    suspend fun updateCow(cowEntity: CowEntity) = cowDao.update(cowEntity)
    suspend fun deleteCow(cowEntity: CowEntity) = cowDao.delete(cowEntity)

//    suspend fun getCowLocalIdByServerId(serverId: String): Int? = cowDao.getCowLocalIdByServerId(serverId)
//    suspend fun getEditedCows(): List<Cow> = cowDao.getEditedCows()
//    suspend fun getDeletedCows(): List<Cow> = cowDao.getDeletedCows()

}