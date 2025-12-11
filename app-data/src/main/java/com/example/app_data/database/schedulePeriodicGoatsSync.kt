//package com.example.database
//
//import android.content.Context
//import androidx.work.Constraints
//import androidx.work.ExistingPeriodicWorkPolicy
//import androidx.work.NetworkType
//import androidx.work.PeriodicWorkRequestBuilder
//import androidx.work.WorkManager
//import java.util.concurrent.TimeUnit
//
//private const val WORK_TAG = "GoatSyncWork"
//fun schedulePeriodicGoatsSync(context: Context) {
//    val workName = SyncGoatsWorker.WORK_NAME
//
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .build()
//
//    val periodicWorkRequest = PeriodicWorkRequestBuilder<SyncGoatsWorker>(
//        repeatInterval = 1,
//        repeatIntervalTimeUnit = TimeUnit.HOURS
//    )
//        .addTag(WORK_TAG)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        workName,
//        ExistingPeriodicWorkPolicy.REPLACE,
//        periodicWorkRequest
//        )
//}