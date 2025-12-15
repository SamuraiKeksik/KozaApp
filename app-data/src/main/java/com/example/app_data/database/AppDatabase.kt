package com.example.app_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.SicknessType
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.Weight
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.animals.goats.GoatDao
import com.example.database.Converters


@Database(entities = [
    GoatEntity::class,
    MilkYield::class,
    SicknessType::class,
    Sickness::class,
    Vaccination::class,
    Weight::class,
], version = 13, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goatDao(): GoatDao
    //abstract fun advertisementDao(): AdvertisementDao
    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AppDatabase::class.java, "animals_database")
                    .fallbackToDestructiveMigration() //ToDo: Сделать правильную миграцию
                    .build()
                    .also { Instance = it }
            }
        }
    }
}