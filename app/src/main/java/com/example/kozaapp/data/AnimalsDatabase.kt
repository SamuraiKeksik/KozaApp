package com.example.kozaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.goats.data.model.GoatDao

@Database(entities = [Goat::class], version = 3, exportSchema = false)
abstract class AnimalsDatabase : RoomDatabase() {
    abstract fun goatDao(): GoatDao
    companion object{
        @Volatile
        private var Instance: AnimalsDatabase? = null

        fun getDatabase(context: Context): AnimalsDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AnimalsDatabase::class.java, "animals_database")
                    .fallbackToDestructiveMigration() //ToDo: Сделать правильную миграцию
                    .build()
                    .also { Instance = it }
            }
        }
    }
}