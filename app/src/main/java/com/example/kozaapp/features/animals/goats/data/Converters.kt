package com.example.kozaapp.features.animals.goats.data

import androidx.room.TypeConverter
import com.example.kozaapp.features.animals.model.Breed
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Status

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
}