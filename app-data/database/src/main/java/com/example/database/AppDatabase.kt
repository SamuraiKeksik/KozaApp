package com.example.database

import android.content.Context

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