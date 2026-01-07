package com.example.app_data.database

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app_data.R
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsDao
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.SicknessType
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.Weight
import com.example.app_data.animals.goats.GoatDao
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.dictionary.ArticleEntity
import com.example.app_data.dictionary.DictionaryDao
import com.example.database.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


@Database(
    entities = [
        GoatEntity::class,
        MilkYield::class,
        SicknessType::class,
        Sickness::class,
        Vaccination::class,
        Weight::class,
        ArticleEntity::class
    ], version = 15, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goatDao(): GoatDao
    abstract fun animalsDao(): AnimalsDao
    abstract fun dictionaryDao(): DictionaryDao

    //abstract fun advertisementDao(): AdvertisementDao
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "animals_database")
                    .fallbackToDestructiveMigration() //ToDo: Сделать правильную миграцию
                    .createFromAsset("database/prepopulated_database.db")
                    .addCallback(dbCallBack(context))
                    .build()
                    .also { Instance = it }
            }
        }


        //При открытии БД заполняем таблицу SicknessType
        fun dbCallBack(context: Context): RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = getDatabase(context).animalsDao()
                    val list = dao.getAllSicknessTypes().first()
                    val vaccination = list.firstOrNull() { it.id == 3 }
                    if ( vaccination == null ) {
                        dao.insertSicknessType(
                            SicknessType(
                                id = 1,
                                name = "Неизвестно",
                                description = "",
                                animalType = AnimalType.Unknown
                            )
                        )
                        dao.insertSicknessType(
                            SicknessType(
                                id = 2,
                                name = "Тимпания ",
                                description = context.resources.getString(R.string.tempania_description),
                                animalType = AnimalType.Goat
                            )
                        )
                        dao.insertSicknessType(
                            SicknessType(
                                id = 3,
                                name = "Сибирская язва",
                                description = "",
                                animalType = AnimalType.Goat
                            )
                        )
                    }
                }
            }

        }

    }
}

