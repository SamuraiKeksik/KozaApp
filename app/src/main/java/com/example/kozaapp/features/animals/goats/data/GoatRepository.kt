package com.example.kozaapp.features.animals.goats.data

import com.example.kozaapp.features.animals.goats.data.schemas.toGoatModel
import com.example.kozaapp.features.animals.goats.data.schemas.toGoatRequest
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoatRepository @Inject constructor(
    private val localDataSource: GoatLocalDataSource,
    private val remoteDataSource: GoatRemoteDataSource,
){
    //LocalPart
    fun getAllGoatsStream(): Flow<List<Goat>> = localDataSource.getAllGoatsStream()

    fun getGoatStream(id: Int): Flow<Goat?> = localDataSource.getGoatStream(id)

    suspend fun insertGoat(goat: Goat) {
        val changedGoat = goat.copy(needsSync = true)
        localDataSource.insertGoat(changedGoat)
        updateGoatOnServer(changedGoat)
    }

    suspend fun updateGoat(goat: Goat) {
        val changedGoat = goat.copy(needsSync = true)
        localDataSource.updateGoat(changedGoat)
        updateGoatOnServer(changedGoat)
    }

    suspend fun deleteGoat(goat: Goat){
        if (goat.serverId != null){
            val changedGoat = goat.copy(isDeleted = true)
            localDataSource.updateGoat(changedGoat)
            deleteGoatOnServer(goat)
        }
        else{
            localDataSource.deleteGoat(goat)
        }
    }


    //Server part
    suspend fun copyGoatsFromServer() {
        val remoteGoatsList = remoteDataSource.getGoatsList()

        val localGoatsList = remoteGoatsList.map { it.toGoatModel() }
        localDataSource.insertGoatList(localGoatsList)
    }

    suspend fun deleteGoatOnServer(goat: Goat){
        if (!goat.isDeleted)
            return

        if (goat.serverId == null){
            localDataSource.deleteGoat(goat)
            return
        } else {
            val result = remoteDataSource.deleteGoat(goat.serverId)
            if (result?.isDeleted ?: false){
                localDataSource.deleteGoat(goat)
            }
        }
    }

    suspend fun updateGoatOnServer(goat: Goat){
        if (!goat.needsSync)
            return

        if (goat.serverId == null){
            val goatResponse = remoteDataSource.createGoat(goat.toGoatRequest())
            if (goatResponse != null){
                localDataSource.updateGoat(goatResponse.toGoatModel())
            }
        } else {
            val goatResponse = remoteDataSource.updateGoat(
                goat.serverId,
                goat.toGoatRequest(),
            )
            if (goatResponse != null){
                localDataSource.updateGoat(goatResponse.toGoatModel())
            }
        }
    }
}