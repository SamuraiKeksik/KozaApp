package com.example.app_data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.app_data.dictionary.DictionaryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DictionaryWorker @AssistedInject constructor
    (
    private val dictionaryRepository: DictionaryRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            Log.d("DictionaryWorker", "doWork")
            try {
                dictionaryRepository.synchronizeArticlesWithServer()
                dictionaryRepository.synchronizeSicknessTypesWithServer()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
