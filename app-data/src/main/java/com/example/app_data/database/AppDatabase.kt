package com.example.app_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_data.dictionary.ArticleEntity
import com.example.app_data.dictionary.DictionaryDao
import com.example.database.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


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
                    //.createFromAsset("database/prepopulated_database.db")
                    .addCallback(dbSicknessTypesCallBack(context))
                    .addCallback(dbDictionaryCallBack(context))
                    .build()
                    .also { Instance = it }
            }
        }


        //При открытии БД заполняем таблицу SicknessType
        fun dbSicknessTypesCallBack(context: Context): RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val animalsDao = getDatabase(context).animalsDao()
                    val sicknessTypesList = animalsDao.getAllSicknessTypes().first()
                    val sicknessType = sicknessTypesList.firstOrNull() { it.id == 3 }
                    if ( sicknessType == null ) {
                        animalsDao.insertSicknessType(
                            SicknessType(
                                id = 1,
                                name = "Неизвестно",
                                description = "",
                                animalType = AnimalType.Unknown
                            )
                        )
                        animalsDao.insertSicknessType(
                            SicknessType(
                                id = 2,
                                name = "Тимпания ",
                                description = context.resources.getString(R.string.tempania_description),
                                animalType = AnimalType.Goat
                            )
                        )
                        animalsDao.insertSicknessType(
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

        fun dbDictionaryCallBack(context: Context): RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dictionaryDao = getDatabase(context).dictionaryDao()
                    val articlesList = dictionaryDao.getAllArticles().first()
                    val article = articlesList.firstOrNull() { it.id == 1 }
                    if ( article == null ) {
                        dictionaryDao.insertArticle(
                            ArticleEntity(
                                id = 1,
                                name = "Как кормить коз для увеличения удоя",
                                description = context.resources.getString(R.string.goat_feeding_article_description),
                                animalType = AnimalType.Goat,
                                category = ArticleCategory.FEEDING
                            )
                        )
                    }
                }
            }

        }

    }
}

