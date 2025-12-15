package com.example.app_data.animals.goats
//
//import com.example.app_data.animals.goats.Goat
//import com.example.kozaapp.features.animals.goats.data.schemas.toGoatModel
//import com.example.kozaapp.features.animals.goats.data.schemas.toGoatRequest
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

interface GoatRepository{
    val goatsList: Flow<List<GoatEntity>>
    fun getGoat(id: UUID): Flow<GoatEntity?>
    suspend fun insertGoat(goatEntity: GoatEntity)
    suspend fun updateGoat(goatEntity: GoatEntity)
    suspend fun deleteGoat(goatEntity: GoatEntity)

}

class DefaultGoatRepository @Inject constructor(
    private val localDataSource: GoatLocalDataSource,
    private val remoteDataSource: GoatRemoteDataSource,
):GoatRepository{

    override val goatsList: Flow<List<GoatEntity>> = localDataSource.getAllGoatsStream()
    override fun getGoat(id: UUID): Flow<GoatEntity?> = localDataSource.getGoatStream(id)

    override suspend fun insertGoat(goatEntity: GoatEntity) {
        val changedGoat = goatEntity.copy(isEdited = true)
        localDataSource.insertGoat(changedGoat)
    }

    override suspend fun updateGoat(goatEntity: GoatEntity) {
        val changedGoat = goatEntity.copy(isEdited = true)
        localDataSource.updateGoat(changedGoat)
    }

    override suspend fun deleteGoat(goatEntity: GoatEntity) = localDataSource.deleteGoat(goatEntity)

//    suspend fun deleteGoat(goat: Goat){
//        if (goat.serverId != null){
//            val changedGoat = goat.copy(isDeleted = true)
//            localDataSource.updateGoat(changedGoat)
//        }
//        else{
//            localDataSource.deleteGoat(goat)
//        }
//    }

//    //Server part
//    suspend fun syncGoats(){
//        syncDeletedGoats()
//        syncEditedGoats()
//        copyGoatsFromServer()
//    }
//
//    private suspend fun copyGoatsFromServer() {
//        try {
//            val remoteGoatsList = remoteDataSource.getGoatsList()
//            val localGoatsList = remoteGoatsList.map { it.toGoatModel() }
//            localGoatsList.forEach { goat ->
//                if (goat.serverId != null){
//                    val localId = localDataSource.getGoatLocalIdByServerId(goat.serverId)
//                    if (localId != null){
//                        val updatedGoat = goat.copy(id = localId)
//                        localDataSource.updateGoat(updatedGoat)
//                    } else {
//                        localDataSource.insertGoat(goat)
//                    }
//                }
//            }
//        } catch (e: Exception){
//            //ToDo: Сделать обработку ошибки
//        }
//
//    }
//
//    private suspend fun syncDeletedGoats(){
//        localDataSource.getDeletedGoats().forEach { goat ->
//            if (goat.serverId != null){
//                try {
//                    remoteDataSource.deleteGoat(goat.serverId)
//                    localDataSource.deleteGoat(goat)
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                localDataSource.deleteGoat(goat)
//            }
//        }
//    }
//
//    private suspend fun syncEditedGoats(){
//        localDataSource.getEditedGoats().forEach { goat ->
//            if (goat.serverId == null){
//                try{
//                    val response = remoteDataSource.createGoat(goat.toGoatRequest())
//                    response?.let {
//                        val createdGoat = it.toGoatModel().copy(id = goat.id)
//                        localDataSource.updateGoat(createdGoat)
//                    }
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                try {
//                    val response = remoteDataSource.updateGoat(goat.serverId, goat.toGoatRequest())
//                    response?.let {
//                        val updatedGoat = it.toGoatModel().copy(goat.id)
//                        localDataSource.updateGoat(updatedGoat)
//                        }
//
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            }
//        }
//    }


}