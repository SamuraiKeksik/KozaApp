package com.example.app_data.animals.chickens
//
//import com.example.app_data.animals.chickens.Chicken
//import com.example.kozaapp.features.animals.chickens.data.schemas.toChickenModel
//import com.example.kozaapp.features.animals.chickens.data.schemas.toChickenRequest
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsRepository
import com.example.app_data.animals.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

interface ChickenRepository {
    val chickensList: Flow<List<ChickenEntity>>
    suspend fun getChickensModelsList(): List<ChickenModel>
    fun getChickenChildren(id: UUID): Flow<List<ChickenEntity>>
    suspend fun getChickenName(id: UUID): String?
    suspend fun getChickenGender(id: UUID): Gender
    fun getChickenModel(id: UUID): Flow<ChickenModel?>
    suspend fun insertChicken(chickenEntity: ChickenEntity)
    suspend fun updateChicken(chickenEntity: ChickenEntity)
    suspend fun deleteChicken(chickenEntity: ChickenEntity)

}

class DefaultChickenRepository @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val localDataSource: ChickenLocalDataSource,
) : ChickenRepository {

    override val chickensList: Flow<List<ChickenEntity>> = localDataSource.getAllChickensStream()
    override suspend fun getChickensModelsList(): List<ChickenModel> = localDataSource.getChickensModelsList()

    override fun getChickenChildren(id: UUID): Flow<List<ChickenEntity>> {
        return localDataSource.getAllChickensStream().map { childrenList ->
            childrenList.filter {
                it.id != id && (it.motherId == id || it.fatherId == id)
            }
        }
    }

    override suspend fun getChickenName(id: UUID): String? = localDataSource.getChickenNameStream(id)
    override suspend fun getChickenGender(id: UUID): Gender =
        Gender.valueOf(localDataSource.getChickenGender(id))

    override fun getChickenModel(id: UUID): Flow<ChickenModel?> = localDataSource.getChickenModelStream(id)


    override suspend fun insertChicken(chickenEntity: ChickenEntity) {
        val changedChicken = chickenEntity.copy(isEdited = true)
        localDataSource.insertChicken(changedChicken)
        val chickenAgeInDays = LocalDate.now().toEpochDay() - chickenEntity.birthDate
        animalsRepository.createInitialVaccinations(chickenEntity.id, chickenAgeInDays.toInt(), AnimalType.CHICKEN)
    }

    override suspend fun updateChicken(chickenEntity: ChickenEntity) {
        val changedChicken = chickenEntity.copy(isEdited = true)
        localDataSource.updateChicken(changedChicken)
    }

    override suspend fun deleteChicken(chickenEntity: ChickenEntity) = localDataSource.deleteChicken(chickenEntity)

//    suspend fun deleteChicken(chicken: Chicken){
//        if (chicken.serverId != null){
//            val changedChicken = chicken.copy(isDeleted = true)
//            localDataSource.updateChicken(changedChicken)
//        }
//        else{
//            localDataSource.deleteChicken(chicken)
//        }
//    }

//    //Server part
//    suspend fun syncChickens(){
//        syncDeletedChickens()
//        syncEditedChickens()
//        copyChickensFromServer()
//    }
//
//    private suspend fun copyChickensFromServer() {
//        try {
//            val remoteChickensList = remoteDataSource.getChickensList()
//            val localChickensList = remoteChickensList.map { it.toChickenModel() }
//            localChickensList.forEach { chicken ->
//                if (chicken.serverId != null){
//                    val localId = localDataSource.getChickenLocalIdByServerId(chicken.serverId)
//                    if (localId != null){
//                        val updatedChicken = chicken.copy(id = localId)
//                        localDataSource.updateChicken(updatedChicken)
//                    } else {
//                        localDataSource.insertChicken(chicken)
//                    }
//                }
//            }
//        } catch (e: Exception){
//            //ToDo: Сделать обработку ошибки
//        }
//
//    }
//
//    private suspend fun syncDeletedChickens(){
//        localDataSource.getDeletedChickens().forEach { chicken ->
//            if (chicken.serverId != null){
//                try {
//                    remoteDataSource.deleteChicken(chicken.serverId)
//                    localDataSource.deleteChicken(chicken)
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                localDataSource.deleteChicken(chicken)
//            }
//        }
//    }
//
//    private suspend fun syncEditedChickens(){
//        localDataSource.getEditedChickens().forEach { chicken ->
//            if (chicken.serverId == null){
//                try{
//                    val response = remoteDataSource.createChicken(chicken.toChickenRequest())
//                    response?.let {
//                        val createdChicken = it.toChickenModel().copy(id = chicken.id)
//                        localDataSource.updateChicken(createdChicken)
//                    }
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                try {
//                    val response = remoteDataSource.updateChicken(chicken.serverId, chicken.toChickenRequest())
//                    response?.let {
//                        val updatedChicken = it.toChickenModel().copy(chicken.id)
//                        localDataSource.updateChicken(updatedChicken)
//                        }
//
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            }
//        }
//    }


}