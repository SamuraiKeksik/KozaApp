package com.example.app_data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
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
                val articlesCount = dictionaryRepository.synchronizeArticlesWithServer()
                val sicknessTypesCount = dictionaryRepository.synchronizeSicknessTypesWithServer()
                if (articlesCount > 0 || sicknessTypesCount > 0)
                    sendNotification(articlesCount, sicknessTypesCount)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }

    private fun sendNotification(articlesCount: Int, sicknessTypesCount: Int) {
        val title = "Синхронизация завершена!"
        var message = ""
        if (articlesCount > 0 && sicknessTypesCount <= 0)
            message = "Скачано $articlesCount статей."
        if (articlesCount <= 0 && sicknessTypesCount > 0)
            message = "Скачано $sicknessTypesCount болезней."
        if (articlesCount > 0 && sicknessTypesCount > 0)
            message = "Скачано $articlesCount статей и $sicknessTypesCount болезней."

        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val name = "KozaApp"
        val descriptionText = "KozaApp server sync"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "work_manager_notifications"
        const val NOTIFICATION_ID = 1
    }
}
