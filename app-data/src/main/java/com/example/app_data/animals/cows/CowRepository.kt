package com.example.app_data.animals.cows
//
//import com.example.app_data.animals.cows.Cow
//import com.example.kozaapp.features.animals.cows.data.schemas.toCowModel
//import com.example.kozaapp.features.animals.cows.data.schemas.toCowRequest
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsRepository
import com.example.app_data.animals.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

interface CowRepository {
    val cowsList: Flow<List<CowEntity>>
    suspend fun getCowsModelsList(): List<CowModel>
    fun getCowChildren(id: UUID): Flow<List<CowEntity>>
    suspend fun getCowName(id: UUID): String?
    suspend fun getCowGender(id: UUID): Gender
    fun getCowModel(id: UUID): Flow<CowModel?>
    suspend fun insertCow(cowEntity: CowEntity)
    suspend fun updateCow(cowEntity: CowEntity)
    suspend fun deleteCow(cowEntity: CowEntity)

}

class DefaultCowRepository @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val localDataSource: CowLocalDataSource,
) : CowRepository {

    override val cowsList: Flow<List<CowEntity>> = localDataSource.getAllCowsStream()
    override suspend fun getCowsModelsList(): List<CowModel> = localDataSource.getCowsModelsList()

    override fun getCowChildren(id: UUID): Flow<List<CowEntity>> {
        return localDataSource.getAllCowsStream().map { childrenList ->
            childrenList.filter {
                it.id != id && (it.motherId == id || it.fatherId == id)
            }
        }
    }

    override suspend fun getCowName(id: UUID): String? = localDataSource.getCowNameStream(id)
    override suspend fun getCowGender(id: UUID): Gender =
        Gender.valueOf(localDataSource.getCowGender(id))

    override fun getCowModel(id: UUID): Flow<CowModel?> = localDataSource.getCowModelStream(id)


    override suspend fun insertCow(cowEntity: CowEntity) {
        val changedCow = cowEntity.copy(isEdited = true)
        localDataSource.insertCow(changedCow)
        val cowAgeInDays = LocalDate.now().toEpochDay() - cowEntity.birthDate
        animalsRepository.createInitialVaccinations(cowEntity.id, cowAgeInDays.toInt(), AnimalType.COW)
    }

    override suspend fun updateCow(cowEntity: CowEntity) {
        val changedCow = cowEntity.copy(isEdited = true)
        localDataSource.updateCow(changedCow)
    }

    override suspend fun deleteCow(cowEntity: CowEntity) = localDataSource.deleteCow(cowEntity)

//    suspend fun deleteCow(cow: Cow){
//        if (cow.serverId != null){
//            val changedCow = cow.copy(isDeleted = true)
//            localDataSource.updateCow(changedCow)
//        }
//        else{
//            localDataSource.deleteCow(cow)
//        }
//    }

//    //Server part
//    suspend fun syncCows(){
//        syncDeletedCows()
//        syncEditedCows()
//        copyCowsFromServer()
//    }
//
//    private suspend fun copyCowsFromServer() {
//        try {
//            val remoteCowsList = remoteDataSource.getCowsList()
//            val localCowsList = remoteCowsList.map { it.toCowModel() }
//            localCowsList.forEach { cow ->
//                if (cow.serverId != null){
//                    val localId = localDataSource.getCowLocalIdByServerId(cow.serverId)
//                    if (localId != null){
//                        val updatedCow = cow.copy(id = localId)
//                        localDataSource.updateCow(updatedCow)
//                    } else {
//                        localDataSource.insertCow(cow)
//                    }
//                }
//            }
//        } catch (e: Exception){
//            //ToDo: Сделать обработку ошибки
//        }
//
//    }
//
//    private suspend fun syncDeletedCows(){
//        localDataSource.getDeletedCows().forEach { cow ->
//            if (cow.serverId != null){
//                try {
//                    remoteDataSource.deleteCow(cow.serverId)
//                    localDataSource.deleteCow(cow)
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                localDataSource.deleteCow(cow)
//            }
//        }
//    }
//
//    private suspend fun syncEditedCows(){
//        localDataSource.getEditedCows().forEach { cow ->
//            if (cow.serverId == null){
//                try{
//                    val response = remoteDataSource.createCow(cow.toCowRequest())
//                    response?.let {
//                        val createdCow = it.toCowModel().copy(id = cow.id)
//                        localDataSource.updateCow(createdCow)
//                    }
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            } else {
//                try {
//                    val response = remoteDataSource.updateCow(cow.serverId, cow.toCowRequest())
//                    response?.let {
//                        val updatedCow = it.toCowModel().copy(cow.id)
//                        localDataSource.updateCow(updatedCow)
//                        }
//
//                } catch (e: Exception){
//                    //ToDo: Сделать обработку ошибки
//                }
//            }
//        }
//    }


}