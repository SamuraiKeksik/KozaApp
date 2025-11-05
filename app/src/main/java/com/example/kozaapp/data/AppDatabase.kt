package com.example.kozaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kozaapp.features.advertisements.data.model.Advertisement
import com.example.kozaapp.features.advertisements.data.model.AdvertisementDao
import com.example.kozaapp.data.Converters
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.goats.data.model.GoatDao

@Database(entities = [Goat::class, Advertisement::class], version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goatDao(): GoatDao
    abstract fun advertisementDao(): AdvertisementDao
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