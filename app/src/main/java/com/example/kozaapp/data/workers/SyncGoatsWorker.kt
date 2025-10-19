package com.example.kozaapp.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.kozaapp.data.MainRepository
import com.example.kozaapp.features.animals.goats.data.GoatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val TAG = "SyncGoatsWorker"

@HiltWorker
class SyncGoatsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val goatRepository: GoatRepository,
    private val mainRepository: MainRepository,
) : CoroutineWorker(appContext, workerParameters){

    override suspend fun doWork(): Result {
        //Если аутентификация не прошла то возврат ошибки
        if (mainRepository.getCurrentUser() == null){
            Log.w(TAG, "Синхронизация пропущена: пользователь не аутентифицирован.")
            return Result.failure()
        }

        return try {
            Log.i(TAG, "Синхронизация коз началась.")
            goatRepository.syncGoats()
            Log.i(TAG, "Синхронизация коз завершена успешно.")
            Result.success()
        }catch (e: Exception){
            Log.e(TAG, "Синхронизация не получилась. Ошибка: ${e.message}")
            Result.failure()
        }
    }
    companion object {
        const val WORK_NAME = "SyncGoatsWork"
    }

}