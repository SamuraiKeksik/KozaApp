package com.example.database

import androidx.room.TypeConverter
import com.example.advertisements.AdvertisementType
import com.example.app_data.animals.Breed
import com.example.app_data.animals.Gender
import com.example.app_data.animals.Status

//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }
    @TypeConverter
    fun toGender(genderString: String): Gender {
        return Gender.valueOf(genderString)
    }
    @TypeConverter
    fun fromBreed(breed: Breed): String {
        return breed.name
    }
    @TypeConverter
    fun toBreed(breedString: String): Breed {
        return Breed.valueOf(breedString)
    }
    @TypeConverter
    fun fromStatus(status: Status): String {
        return status.name
    }
    @TypeConverter
    fun toStatus(statusString: String): Status {
        return Status.valueOf(statusString)
    }

    @TypeConverter
    fun fromAdvertisementType(advertisementType: AdvertisementType): String {
        return advertisementType.name
    }
    @TypeConverter
    fun toAdvertisementType(advertisementTypeString: String): AdvertisementType {
        return AdvertisementType.valueOf(advertisementTypeString)
    }

//    private val gson = Gson()
//
//    @TypeConverter
//    fun fromStringList(list: List<String>): String{
//        return gson.toJson(list)
//    }
//
//    @TypeConverter
//    fun toStringList(jsonString: String): List<String>? {
//        val listType = object : TypeToken<List<String>>() {}.type
//        return gson.fromJson(jsonString, listType)
//    }
}