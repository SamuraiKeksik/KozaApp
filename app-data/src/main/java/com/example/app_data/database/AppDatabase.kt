package com.example.app_data.animals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.advertisements.Advertisement
import com.example.database.Converters


@Database(entities = [Goat::class], version = 11, exportSchema = false)
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